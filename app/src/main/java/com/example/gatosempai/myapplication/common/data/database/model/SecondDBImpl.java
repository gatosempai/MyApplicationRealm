package com.example.gatosempai.myapplication.common.data.database.model;

import com.example.gatosempai.myapplication.common.data.database.handler.BaseRealmHandler;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by oruizp on 11/30/17.
 */

public class SecondDBImpl extends BaseRealmHandler implements SecondDB {
    public SecondDBImpl(Realm realm) {
        super(realm);
    }

    @Override
    public Observable<SecondModel> getSecondData(long secondId) {
        return getObject(secondId);
    }

    @Override
    public Observable<SecondModel> addSecondData(SecondModel data) {
        return createObject(data);
    }

    @Override
    public Observable<SecondModel> updateSecondData(SecondModel data) {
        return updateObject(data);
    }

    @Override
    public Observable<Void> deleteSecondData(SecondModel data) {
        return deleteObject(data);
    }

    @Override
    public Class getModelClass() {
        return SecondModel.class;
    }
}
