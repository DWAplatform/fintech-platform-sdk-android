package com.fintechplatform.ui.account.financialdata.payinpayout;

import com.fintechplatform.api.card.api.PaymentCardAPI;
import com.fintechplatform.api.iban.api.IbanAPI;
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB;
import com.fintechplatform.ui.iban.db.IbanPersistanceDB;
import com.fintechplatform.ui.models.DataAccount;

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
    FinancialDataContract.Presenter providesPayInPayOutFinancialDataPresenter(PaymentCardAPI cardAPI, IbanAPI ibanAPI, PaymentCardPersistenceDB paymentCardPersistenceDB, IbanPersistanceDB ibanPersistanceDB){
        return new PayInPayOutFinancialDataPresenter(view, cardAPI, ibanAPI, configuration, ibanPersistanceDB, paymentCardPersistenceDB);
    }

}
