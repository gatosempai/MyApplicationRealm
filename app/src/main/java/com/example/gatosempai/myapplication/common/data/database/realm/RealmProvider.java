package com.example.gatosempai.myapplication.common.data.database.realm;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.security.auth.x500.X500Principal;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by oscar on 16/07/17.
 */

public class RealmProvider {
    private static final String REALM_NAME = "disk_cache";

    private static RealmConfiguration sRealmConfiguration;

    public RealmProvider() {
        throw new AssertionError("No instances allowed.");
    }

    public static void configureRealm(Context context) {
        final RealmKeyProvider keyProvider = new RealmKeyProvider(context);
        final byte[] key = keyProvider.getSecretKey();
        Realm.init(context);
        sRealmConfiguration = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .encryptionKey(key)
                .build();
        Realm.removeDefaultConfiguration();
        Realm.setDefaultConfiguration(sRealmConfiguration);
    }

    public static Realm getInstance() {
        if (sRealmConfiguration != null) {
            return Realm.getInstance(sRealmConfiguration);
        } else {
            throw new IllegalStateException("Must configure realm before instantiation.");
        }
    }

    public static void initEcc() {
        RealmKeyProvider.initECKeys();
    }

    /**
     * Ref: https://medium.com/@ericfu/securely-storing-secrets-in-an-android-application-501f030ae5a3#.6o309gbpf
     */
    private static class RealmKeyProvider {
        //private static final String PROVIDER_NAME = "AndroidKeyStoreBCWorkaround";
        private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
        private static final String KEY_ALIAS = "RSA_KEYS";
        private static final String RSA_MODE = "RSA/NONE/PKCS1Padding";
        private static final String SHARED_PREFENCE_NAME = "mx.com.bancoazteca.bancadigital.common.data.realm.RealmKeyFile";
        private static final String ENCRYPTED_KEY = "RealmKey";
        private static final String ENCRYPTION_ALGORITHM = "RSA";
        private static final String KEY_ALGORITHM = "AES";
        private static final int KEY_SIZE = 512;
        private static final byte[] BYTES = new byte[0];
        private final Context mContext;

        RealmKeyProvider(Context context) {
            mContext = context;
            //System.out.println(ECNameCurved);
            initializeEncryptionKeys();
            //initECKeys();
            generateAndStoreKey();
        }

        private static void initECKeys() {
            try {
                final KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
                keyStore.load(null);
                // Check if keys exist
                if (!keyStore.containsAlias(KEY_ALIAS)) {
                    // Generate a EC key pair for encryption
                    System.out.println("ORP  ecc init");
                    KeyPairGenerator kpg = KeyPairGenerator.getInstance("ECDH", ANDROID_KEY_STORE);
                    ECGenParameterSpec ecParamSpec = new ECGenParameterSpec("secp521r1");
                    kpg.initialize(ecParamSpec, new SecureRandom());
                    KeyPair keyPair = kpg.generateKeyPair();
                    System.out.println("ORP  ecc finish");
                    System.out.println("ORP  ecc private algo: "+keyPair.getPrivate().getAlgorithm());
                    System.out.println("ORP  ecc private format: "+keyPair.getPrivate().getFormat());
                    System.out.println("ORP  ecc private toString: "+keyPair.getPrivate().toString());
                } else System.out.println("ORP  ecc key exist in keystore");
            } catch (KeyStoreException | IOException | CertificateException |
                    NoSuchAlgorithmException | NoSuchProviderException |
                    InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }

        private void initializeEncryptionKeys() {
            try {
                final KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
                keyStore.load(null);
                // Check if keys exist
                if (!keyStore.containsAlias(KEY_ALIAS)) {
                    // Generate a RSA key pair for encryption
                    final Calendar start = Calendar.getInstance();
                    final Calendar end = Calendar.getInstance();
                    end.add(Calendar.YEAR, 30);

                    @SuppressWarnings("deprecation")
                    final KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(mContext)
                            .setAlias(KEY_ALIAS)
                            .setSubject(new X500Principal("CN=" + KEY_ALIAS))
                            .setSerialNumber(BigInteger.TEN)
                            .setStartDate(start.getTime())
                            .setEndDate(end.getTime()).build();
                    final KeyPairGenerator kpg = KeyPairGenerator
                            .getInstance(ENCRYPTION_ALGORITHM, ANDROID_KEY_STORE);
                    kpg.initialize(spec);
                    kpg.generateKeyPair();
                }
            } catch (KeyStoreException | IOException | NoSuchAlgorithmException
                    | CertificateException | NoSuchProviderException
                    | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }

        private void generateAndStoreKey() {
            final SharedPreferences pref = mContext
                    .getSharedPreferences(SHARED_PREFENCE_NAME, Context.MODE_PRIVATE);
            final String existentKey = pref.getString(ENCRYPTED_KEY, "");
            if (existentKey.isEmpty()) {
                final byte[] encryptedKey = rsaEncrypt(generateKey());
                final String enryptedKeyB64 = Base64.encodeToString(encryptedKey, Base64.DEFAULT);
                final SharedPreferences.Editor edit = pref.edit();
                edit.putString(ENCRYPTED_KEY, enryptedKeyB64);
                edit.apply();
            }
        }

        private static byte[] rsaEncrypt(byte[] secret) {
            byte[] values = BYTES;
            try {
                final KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
                keyStore.load(null);
                final KeyStore.PrivateKeyEntry privateKeyEntry =
                        (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
                // Encrypt the text
                final Cipher inputCipher = Cipher.getInstance(RSA_MODE);
                inputCipher
                        .init(Cipher.ENCRYPT_MODE, privateKeyEntry.getCertificate().getPublicKey());
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                final CipherOutputStream cipherOutputStream =
                        new CipherOutputStream(outputStream, inputCipher);
                cipherOutputStream.write(secret);
                cipherOutputStream.close();
                values = outputStream.toByteArray();
            } catch (UnrecoverableEntryException | NoSuchAlgorithmException | NoSuchPaddingException
                    | CertificateException | IOException //| NoSuchProviderException
                    | InvalidKeyException | KeyStoreException e) {
                e.printStackTrace();
            }
            return values;
        }

        /**
         * Ref: https://android-developers.blogspot.se/2013/02/using-cryptography-to-store-credentials.html
         *
         * @return A random key to use in Realm encryption
         */
        private static byte[] generateKey() {
            SecretKey key = null;
            try {
                final SecureRandom secureRandom = new SecureRandom();
                // Do *not* seed secureRandom! Automatically seeded from system entropy.
                final KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
                keyGenerator.init(KEY_SIZE, secureRandom);
                key = keyGenerator.generateKey();
            } catch (final NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return (key == null) ? null : key.getEncoded();
        }

        private byte[] getSecretKey() {
            byte[] key = BYTES;
            final SharedPreferences pref = mContext
                    .getSharedPreferences(SHARED_PREFENCE_NAME, Context.MODE_PRIVATE);
            final String existentKey = pref.getString(ENCRYPTED_KEY, null);
            if (existentKey != null) {
                final byte[] enryptedKeyB64 = existentKey.getBytes();
                final byte[] encryptedKey = Base64.decode(enryptedKeyB64, Base64.DEFAULT);
                key = rsaDecrypt(encryptedKey);
            }
            return key;
        }

        private static byte[] rsaDecrypt(byte[] encrypted) {
            final ArrayList<Byte> values = new ArrayList<>(64);
            try {
                final KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
                keyStore.load(null);
                final KeyStore.PrivateKeyEntry privateKeyEntry =
                        (KeyStore.PrivateKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
                final Cipher output = Cipher.getInstance(RSA_MODE);
                output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());
                final CipherInputStream cipherInputStream = new CipherInputStream(
                        new ByteArrayInputStream(encrypted), output);
                int nextByte;
                while ((nextByte = cipherInputStream.read()) != -1) {
                    //noinspection NumericCastThatLosesPrecision
                    values.add((byte) nextByte);
                }
            } catch (UnrecoverableEntryException | NoSuchAlgorithmException | NoSuchPaddingException
                    | CertificateException | IOException //| NoSuchProviderException
                    | InvalidKeyException | KeyStoreException e) {
                e.printStackTrace();
            }

            final byte[] bytes = new byte[values.size()];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i);
            }
            return bytes;
        }
    }
}
