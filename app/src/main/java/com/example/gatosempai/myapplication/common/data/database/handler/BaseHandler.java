package com.example.gatosempai.myapplication.common.data.database.handler;

import com.example.gatosempai.myapplication.common.data.database.model.BaseModel;

import java.util.List;

import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by oscar on 15/07/17.
 */

public abstract class BaseHandler<T extends RealmObject & BaseModel> {

    // Get
    abstract Observable<T> getObject(long id);
    abstract Observable<List<T>> listObjects();

    // Get on UI
    abstract Observable<T> getLoopyObject(long id);
    abstract Observable<RealmResults<T>> listLoopyObjects();

    // Create
    abstract Observable<T> createObject(T object);

    // Update
    abstract Observable<T> updateObject(T object);

    // Delete
    abstract Observable<Void> deleteObject(T object);
}
