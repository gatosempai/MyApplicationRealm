package com.example.gatosempai.myapplication.common.data.database.handler;

import com.example.gatosempai.myapplication.common.data.database.model.BaseModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by oscar on 15/07/17.
 */

public abstract class BaseRealmHandler<T extends RealmObject & BaseModel> extends BaseHandler<T>  {
    private final Realm mRealm;
    private final String ID = "id";

    protected BaseRealmHandler(Realm realm) {
        mRealm = realm;
    }

    @Override
    public Observable<T> getObject(long id) {
        T result = mRealm.where(getModelClass())
                .equalTo(ID, id)
                .findFirst();
        if (result != null) {
            result = mRealm.copyFromRealm(result);
        }
        return Observable.just(result);
    }

    public abstract Class<T> getModelClass();

    //================================================================================
    // Get
    //================================================================================

    @Override
    public Observable<List<T>> listObjects() {
        return Observable.just(mRealm.copyFromRealm(mRealm.where(getModelClass())
                .findAll()));

    }

    @Override
    public Observable<T> getLoopyObject(long id) {
        return mRealm.where(getModelClass())
                .equalTo(ID, id)
                .findFirstAsync()
                .asObservable()
                .filter(new Func1<RealmObject, Boolean>() {
                    @Override
                    public Boolean call(RealmObject realmObject) {
                        return realmObject.isLoaded();
                    }
                })
                .map(new Func1<RealmObject, T>() {
                    @Override
                    public T call(RealmObject realmObject) {
                        return (T) realmObject;
                    }
                });
    }

    //================================================================================
    // Get managed objects for Looper threads
    //================================================================================

    @Override
    public Observable<RealmResults<T>> listLoopyObjects() {
        return mRealm.where(getModelClass())
                .findAllAsync()
                .asObservable()
                .filter(new Func1<RealmResults<T>, Boolean>() {
                    @Override
                    public Boolean call(RealmResults<T> ts) {
                        return ts.isLoaded();
                    }
                });

    }

    @Override
    public Observable<T> createObject(T object) {
        return doTransaction(object, new Action1<T>() {
            @Override
            public void call(T object1) {
                mRealm.copyToRealmOrUpdate(object1);
            }
        });
    }

    //================================================================================
    // Create
    //================================================================================

    private Observable<T> doTransaction(T object, Action1<T> action1) {
        mRealm.beginTransaction();
        try {
            action1.call(object);
            mRealm.commitTransaction();
            return Observable.just(object);
        } catch (final RuntimeException exception) {
            mRealm.cancelTransaction();
            return Observable.error(exception);
        } catch (final Error error) {
            mRealm.cancelTransaction();
            return Observable.error(error);
        }
    }

    //================================================================================
    // Update
    //================================================================================

    @Override
    public Observable<T> updateObject(T object) {
        return doTransaction(object, new Action1<T>() {
            @Override
            public void call(T object1) {
                mRealm.copyToRealmOrUpdate(object1);
            }
        });
    }

    //================================================================================
    // Delete
    //================================================================================

    @Override
    public Observable<Void> deleteObject(T object) {
        return doTransaction(object, new Action1<T>() {
            @Override
            public void call(T o) {
                mRealm.where(BaseRealmHandler.this.getModelClass())
                        .equalTo(ID, o.getId())
                        .findAll()
                        .first()
                        .deleteFromRealm();
            }
        })
                .map(new Func1<T, Void>() {
                    @Override
                    public Void call(T delete) {
                        return null;
                    }
                });

    }
}
