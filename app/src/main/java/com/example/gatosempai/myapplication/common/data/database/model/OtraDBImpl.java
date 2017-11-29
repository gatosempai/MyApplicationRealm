package com.example.gatosempai.myapplication.common.data.database.model;

import com.example.gatosempai.myapplication.common.data.database.handler.BaseRealmHandler;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by oruizp on 11/29/17.
 */

public class OtraDBImpl extends BaseRealmHandler<OtraModel> implements OtraDB {


    public OtraDBImpl(Realm realm) {
        super(realm);
    }

    @Override
    public Observable<OtraModel> getOtraData(long otraId) {
        return getObject(otraId);
    }

    @Override
    public Observable<OtraModel> addOtraData(OtraModel data) {
        return createObject(data);
    }

    @Override
    public Observable<OtraModel> updateOtraData(OtraModel data) {
        return updateObject(data);
    }

    @Override
    public Observable<Void> deleteOtraData(OtraModel data) {
        return deleteObject(data);
    }

    @Override
    public Class<OtraModel> getModelClass() {
        return OtraModel.class;
    }
}
