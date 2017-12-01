package com.example.gatosempai.myapplication.common.data.database.realm;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by oruizp on 4/7/16.
 */
public class CryptoUtils {

    //    private static final int PBKDF2_ITERATIONS = 66666; //security level recommended
    private static final int PBKDF2_ITERATIONS = 1000;
    private static final int OUTPUT_SIZE = 256;
    private static final int OUTPUT_IV = 128;
    private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String ENCRYPT_ALGORITHM = "AES";
    private static final String SHA_ALGORITHM = "SHA-256";

    public static byte[] genIv(int size) {
        byte iV[] = null;
        try {
            final SecureRandom secureRandom = new SecureRandom();
            final KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPT_ALGORITHM);
            keyGenerator.init(256, secureRandom);
            final SecretKey key = keyGenerator.generateKey();
            final String pass = toHex(key.getEncoded());
            final KeySpec keySpec = new PBEKeySpec(pass.toCharArray(), key.getEncoded(),
                    PBKDF2_ITERATIONS, OUTPUT_IV);
            final SecretKeyFactory skf = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
            final SecretKey newKey = skf.generateSecret(keySpec);
            iV = newKey.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return iV;

    }

    /**
     * Converts a byte array into a hexadecimal string.
     *
     * @param array the byte array to convert
     * @return a length*2 character string encoding the byte array
     */
    public static String toHex(byte[] array) {
        final BigInteger bi = new BigInteger(1, array);
        final String hex = bi.toString(16);
        final int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * @param key Secret key
     * @return Cryptographic nonce for IV
     */
    public static byte[] generateSessionNonce(byte[] key) {
        byte nonce[] = key;
        try {
            for (int i = 0; i < 2; i++) {
                final Mac hmac = Mac.getInstance("HmacSHA256");
                final SecretKeySpec hMacKey = new SecretKeySpec(key, "HmacSHA256");
                hmac.init(hMacKey);
                hmac.update(nonce);
                nonce = hmac.doFinal();
            }
            nonce = splitKey(nonce);
        } catch (final NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return nonce;

    }

    /**
     * @param key Secret key byte encoded
     * @return cryptographic nonce for IV
     */
    private static byte[] splitKey(byte[] key) {
        final byte[] nonce = new byte[16];
        for (int i = 0; i < nonce.length; i++) {
            nonce[i] = key[i];
        }
        return nonce;
    }

    /**
     * @param passPhrase User password
     * @return Password salted
     */
    public static String saltingPassword(String passPhrase) {
        try {
            final MessageDigest mMessageDigest = MessageDigest.getInstance("SHA-512");
            final byte[] mData = passPhrase.getBytes();
            final byte[] mDigest = mMessageDigest.digest(mData);
            final int mSizeDigest = mDigest.length / 2;
            final byte[] mFirstDigest = new byte[mSizeDigest];
            final byte[] mSecondDigest = new byte[mSizeDigest];
            System.arraycopy(mDigest, 0, mFirstDigest, 0, mSizeDigest);
            System.arraycopy(mDigest, mSizeDigest, mSecondDigest, 0, mSizeDigest);
            final KeySpec mKeySpec = new PBEKeySpec(toHex(mDigest).toCharArray(), mFirstDigest,
                    PBKDF2_ITERATIONS, OUTPUT_SIZE);
            final SecretKeyFactory mSKF = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            final SecretKey mNewKey = mSKF.generateSecret(mKeySpec);
            final SecretKeySpec mSKeySpec = new SecretKeySpec(mNewKey.getEncoded(), "HmacSha256");
            final Mac mMAC = Mac.getInstance("HmacSha256");
            mMAC.init(mSKeySpec);

            return toHex(mMAC.doFinal(mSecondDigest));
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param passPhrase User password
     * @return Salt for key material
     */
    public static byte[] generateSalt(String passPhrase) {
        try {
            final MessageDigest mMessageDigest = MessageDigest.getInstance("SHA-512");
            final byte[] mData = passPhrase.getBytes();
            final byte[] mDigest = mMessageDigest.digest(mData);
            final int mSizeDigest = mDigest.length / 2;
            final byte[] mFirstDigest = new byte[mSizeDigest];
            final byte[] mSecondDigest = new byte[mSizeDigest];
            System.arraycopy(mDigest, 0, mFirstDigest, 0, mSizeDigest);
            System.arraycopy(mDigest, mSizeDigest, mSecondDigest, 0, mSizeDigest);
            final KeySpec mKeySpec = new PBEKeySpec(toHex(mDigest).toCharArray(), mFirstDigest,
                    PBKDF2_ITERATIONS, OUTPUT_SIZE);
            final SecretKeyFactory mSKF = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            final SecretKey mNewKey = mSKF.generateSecret(mKeySpec);
            final SecretKeySpec mSKeySpec = new SecretKeySpec(mNewKey.getEncoded(), "HmacSha256");
            final Mac mMAC = Mac.getInstance("HmacSha256");
            mMAC.init(mSKeySpec);

            return mMAC.doFinal(mSecondDigest);

        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param passPhrase User password
     * @return salt for key material
     */
    public static byte[] generateSaltKS(String passPhrase) {
        final byte[] keyStart = passPhrase.getBytes();
        final int outputKeyLength = 256;

        try {
            final SecureRandom secureRandom = new SecureRandom();
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(outputKeyLength, secureRandom);
            final SecretKey key = keyGenerator.generateKey();

            return key.getEncoded();

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param hex the hex string
     * @return the hex string decoded into a byte array
     */
    public static byte[] fromHex(String hex) {
        final byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

}
