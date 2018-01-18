package com.dwaplatform.android.account.financialdata.ui;

import com.dwaplatform.android.card.db.PaymentCardPersistenceDB;
import com.dwaplatform.android.iban.db.IbanPersistanceDB;
import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class FinancialDataPresenterModule {

    private FinancialDataContract.View view;

    public FinancialDataPresenterModule(FinancialDataContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    FinancialDataContract.Presenter providesFinancialDataPresenter(PaymentCardPersistenceDB paymentCardPersistenceDB, IbanPersistanceDB ibanPersistanceDB){
        return new FinancialDataPresenter(view, ibanPersistanceDB, paymentCardPersistenceDB);
    }
}
