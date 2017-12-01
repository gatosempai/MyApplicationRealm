package com.example.gatosempai.myapplication.common.data.database.model;

import rx.Observable;

/**
 * Created by oruizp on 11/30/17.
 */

public interface SecondDB {
    Observable<SecondModel> getSecondData(long secondId);
    Observable<SecondModel> addSecondData(SecondModel data);
    Observable<SecondModel> updateSecondData(SecondModel data);
    Observable<Void> deleteSecondData(SecondModel data);
}
