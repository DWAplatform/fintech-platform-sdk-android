package com.dwaplatform.android.account.payinpayoutfinancialdata.ui;

import com.dwaplatform.android.card.api.PaymentCardAPI;
import com.dwaplatform.android.card.db.PaymentCardPersistenceDB;
import com.dwaplatform.android.iban.api.IbanAPI;
import com.dwaplatform.android.iban.db.IbanPersistanceDB;
import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class PayInPayOutFinancialDataPresenterModule {

    private PayInPayOutFinancialDataContract.View view;
    private DataAccount configuration;

    public PayInPayOutFinancialDataPresenterModule(PayInPayOutFinancialDataContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    PayInPayOutFinancialDataContract.Presenter providesFinancialDataPresenter(PaymentCardAPI cardAPI, IbanAPI ibanAPI, PaymentCardPersistenceDB paymentCardPersistenceDB, IbanPersistanceDB ibanPersistanceDB){
        return new PayInPayOutFinancialDataPresenter(view, cardAPI, ibanAPI, configuration, ibanPersistanceDB, paymentCardPersistenceDB);
    }
}
