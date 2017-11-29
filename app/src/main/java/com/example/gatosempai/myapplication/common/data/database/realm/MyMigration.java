package com.example.gatosempai.myapplication.common.data.database.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by oruiz on 10/12/16.
 */

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // During a migration, a DynamicRealm is exposed. A DynamicRealm is an untyped variant of a normal Realm, but
        // with the same object creation and query capabilities.
        // A DynamicRealm uses Strings instead of Class references because the Classes might not even exist or have been
        // renamed.

        // Access the Realm schema in order to create, modify or delete classes and their fields.
        final RealmSchema schema = realm.getSchema();
        /***********************************************
         // Version 0
         class Seed                   // add a new model class
         String mValue;
         ************************************************/
        // Migrate from version 1 to version 2
        if (oldVersion == 0) {

            // Create a new class
//            RealmObjectSchema nipSchema = schema.get("Nip");
//            nipSchema.transform(new RealmObjectSchema.Function() {
//                @Override
//                public void apply(DynamicRealmObject obj) {
//                    obj.set("mValue", obj.getString("mValue"));
//                }
//            });
//            oldVersion++;
        }

        /***********************************************
         // Version 1
         class Seed                   // add a new model class
         String mValue;
         ***********************************************/
        // Migrate from version 2 to version 3
        if (oldVersion == 1) {
            // Create a new class
//            RealmObjectSchema seedSchema = schema.get("Seed");
//            seedSchema.transform(new RealmObjectSchema.Function() {
//                @Override
//                public void apply(DynamicRealmObject obj) {
//                    obj.set("mValue", obj.getString("mValue"));
//                }
//            });
//            oldVersion++;
        }
        /***********************************************
         // Version 2
         class DigitalCard                   // add a new model class
         String mValue;
         ***********************************************/
        // Migrate from version 2 to version 3
        if (oldVersion == 2) {
            // Create a new class
//            RealmObjectSchema cardSchema = schema.get("DigitalCard")/*;
//            cardSchema*/.transform(new RealmObjectSchema.Function() {
//                @Override
//                public void apply(DynamicRealmObject obj) {
//                    obj.set("mValue", obj.getString("mValue"));
//                }
//            });
//            oldVersion++;
        }

    }
}
