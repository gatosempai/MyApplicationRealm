package com.example.gatosempai.myapplication.common.data.database.model;

import rx.Observable;

/**
 * Created by oruizp on 11/29/17.
 */

public interface OtraDB {
    Observable<OtraModel> getOtraData(long otraId);
    Observable<OtraModel> addOtraData(OtraModel data);
    Observable<OtraModel> updateOtraData(OtraModel data);
    Observable<Void> deleteOtraData(OtraModel data);
}
