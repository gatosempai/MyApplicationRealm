package com.example.gatosempai.myapplication.common.data.database.model;

import com.example.gatosempai.myapplication.common.data.database.handler.BaseRealmHandler;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by oscar on 29/11/17.
 */

public class PruebaDBImpl extends BaseRealmHandler<PruebaModel> implements PruebaDB {
    public PruebaDBImpl(Realm realm) {
        super(realm);
    }

    @Override
    public Observable<PruebaModel> getPruebaData(long pruebaId) {
        return getObject(pruebaId);
    }

    @Override
    public Observable<PruebaModel> addPruebaData(PruebaModel data) {
        return createObject(data);
    }

    @Override
    public Observable<PruebaModel> updatePruebaData(PruebaModel data) {
        return updateObject(data);
    }

    @Override
    public Observable<Void> deletePruebaData(PruebaModel data) {
        return deleteObject(data);
    }

    @Override
    public Class<PruebaModel> getModelClass() {
        return PruebaModel.class;
    }
}
