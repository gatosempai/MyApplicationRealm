package com.example.gatosempai.myapplication.common.data.database.model;

import rx.Observable;

/**
 * Created by oscar on 29/11/17.
 */

public interface PruebaDB {
    Observable<PruebaModel> getPruebaData(long pruebaId);
    Observable<PruebaModel> addPruebaData(PruebaModel data);
    Observable<PruebaModel> updatePruebaData(PruebaModel data);
    Observable<Void> deletePruebaData(PruebaModel data);
}
