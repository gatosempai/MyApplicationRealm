package com.example.gatosempai.myapplication.common.data.database.model;


import rx.Observable;

/**
 * Created by oruizp on 8/29/17.
 */

public interface InvestmentTermDB {

    Observable<InvestmentTermFolios> getInvestmentTerm(long termId);
    Observable<InvestmentTermFolios> addInvestmentTerm(InvestmentTermFolios term);
    Observable<InvestmentTermFolios> updateInvestmentTerm(InvestmentTermFolios term);
    Observable<Void> deleteInvestmentTerm(InvestmentTermFolios term);
}
