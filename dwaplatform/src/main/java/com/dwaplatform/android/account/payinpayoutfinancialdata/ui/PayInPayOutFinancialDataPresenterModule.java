package com.dwaplatform.android.account.payinpayoutfinancialdata.ui;

import com.dwaplatform.android.card.db.PaymentCardPersistenceDB;
import com.dwaplatform.android.iban.db.IbanPersistanceDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class PayInPayOutFinancialDataPresenterModule {

    private PayInPayOutFinancialDataContract.View view;

    public PayInPayOutFinancialDataPresenterModule(PayInPayOutFinancialDataContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    PayInPayOutFinancialDataContract.Presenter providesFinancialDataPresenter(PaymentCardPersistenceDB paymentCardPersistenceDB, IbanPersistanceDB ibanPersistanceDB){
        return new PayInPayOutFinancialDataPresenter(view, ibanPersistanceDB, paymentCardPersistenceDB);
    }
}
