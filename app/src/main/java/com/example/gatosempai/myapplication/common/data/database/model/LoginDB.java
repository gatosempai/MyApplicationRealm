package com.example.gatosempai.myapplication.common.data.database.model;

import rx.Observable;

public interface LoginDB {
    Observable<LoginInstalledData> getLoginData(long logInId);
    Observable<LoginInstalledData> addLoginData(LoginInstalledData logIn);
    Observable<LoginInstalledData> updateLoginData(LoginInstalledData logIn);
    Observable<Void> deleteLoginData(LoginInstalledData logIn);
}