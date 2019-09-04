package com.fintechplatform.ui.account.financialdata.bank;

import com.fintechplatform.api.card.api.PaymentCardRestAPI;
import com.fintechplatform.api.iban.api.IbanAPI;
import com.fintechplatform.ui.account.financialdata.payinpayout.FinancialDataContract;
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB;
import com.fintechplatform.ui.iban.db.IbanPersistanceDB;
import com.fintechplatform.ui.models.DataAccount;

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
