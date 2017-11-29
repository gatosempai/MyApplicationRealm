package com.example.gatosempai.myapplication.common.data.database.model;


import com.example.gatosempai.myapplication.common.data.database.handler.BaseRealmHandler;

import io.realm.Realm;
import rx.Observable;

public class LoginDBImpl extends BaseRealmHandler<LoginInstalledData> implements LoginDB {

    public LoginDBImpl(Realm realm){
        super(realm);
    }

    @Override
    public Class<LoginInstalledData> getModelClass() {
        return LoginInstalledData.class;
    }

    @Override
    public Observable<LoginInstalledData> getLoginData(long loginInstalledData) {
        return getObject(loginInstalledData);
    }

    @Override
    public Observable<LoginInstalledData> addLoginData(LoginInstalledData loginInstalledData) {
        return createObject(loginInstalledData);
    }

    @Override
    public Observable<LoginInstalledData> updateLoginData(LoginInstalledData loginInstalledData) {
        return updateObject(loginInstalledData);
    }

    @Override
    public Observable<Void> deleteLoginData(LoginInstalledData loginInstalledData) {
        return deleteObject(loginInstalledData);
    }
}