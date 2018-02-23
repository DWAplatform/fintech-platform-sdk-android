package com.fintechplatform.android.account.financialdata.bank;

import com.fintechplatform.android.account.financialdata.payinpayout.FinancialDataContract;
import com.fintechplatform.android.card.api.PaymentCardRestAPI;
import com.fintechplatform.android.card.db.PaymentCardPersistenceDB;
import com.fintechplatform.android.iban.api.IbanAPI;
import com.fintechplatform.android.iban.db.IbanPersistanceDB;
import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 23/02/18.
 */
@Module
public class BankFinancialDataPresenterModule {
    private FinancialDataContract.View view;
    private DataAccount configuration;
    public BankFinancialDataPresenterModule(FinancialDataContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }


    @Provides
    @Singleton
    FinancialDataContract.Presenter providesFinancialDataPresenter(PaymentCardRestAPI cardAPI, IbanAPI ibanAPI, PaymentCardPersistenceDB paymentCardPersistenceDB, IbanPersistanceDB ibanPersistanceDB){
        return new BankFinancialDataPresenter(view, cardAPI, ibanAPI, configuration, ibanPersistanceDB, paymentCardPersistenceDB);
    }
}
