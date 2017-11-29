package com.example.gatosempai.myapplication.common.data.database.model;

import com.example.gatosempai.myapplication.common.data.database.handler.BaseRealmHandler;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by oruizp on 8/29/17.
 */

public class InvestmentTermDBImpl extends BaseRealmHandler<InvestmentTermFolios>
        implements InvestmentTermDB {

    public InvestmentTermDBImpl(Realm realm) {
        super(realm);
    }

    @Override
    public Observable<InvestmentTermFolios> getInvestmentTerm(long termId) {
        return getObject(termId);
    }

    @Override
    public Observable<InvestmentTermFolios> addInvestmentTerm(InvestmentTermFolios term) {
        return createObject(term);
    }

    @Override
    public Observable<InvestmentTermFolios> updateInvestmentTerm(InvestmentTermFolios term) {
        return updateObject(term);
    }

    @Override
    public Observable<Void> deleteInvestmentTerm(InvestmentTermFolios term) {
        return deleteObject(term);
    }

    @Override
    public Class<InvestmentTermFolios> getModelClass() {
        return InvestmentTermFolios.class;
    }
}
