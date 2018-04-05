package com.fintechplatform.android.account.financialdata.payinpayout;

import com.fintechplatform.android.card.api.PaymentCardRestAPI;
import com.fintechplatform.android.card.db.PaymentCardPersistenceDB;
import com.fintechplatform.android.iban.api.IbanAPI;
import com.fintechplatform.android.iban.db.IbanPersistanceDB;
import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class PayInPayOutFinancialDataPresenterModule {

    private FinancialDataContract.View view;
    private DataAccount configuration;

    public PayInPayOutFinancialDataPresenterModule(FinancialDataContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    FinancialDataContract.Presenter providesPayInPayOutFinancialDataPresenter(PaymentCardRestAPI cardAPI, IbanAPI ibanAPI, PaymentCardPersistenceDB paymentCardPersistenceDB, IbanPersistanceDB ibanPersistanceDB){
        return new PayInPayOutFinancialDataPresenter(view, cardAPI, ibanAPI, configuration, ibanPersistanceDB, paymentCardPersistenceDB);
    }

}
