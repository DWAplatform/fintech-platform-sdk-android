package com.dwaplatform.android.account.payinpayoutfinancialdata.ui;

import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.iban.ui.IbanUI;
import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PayInPayOutFinancialDataUIModule {

    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;

    public PayInPayOutFinancialDataUIModule(DataAccount configuration, String hostName, boolean isSandbox) {
        this.configuration = configuration;
        this.hostName = hostName;
        this.isSandbox = isSandbox;
    }

    @Provides
    @Singleton
    PayInPayOutFinancialDataUI providesFinancialDataUI(IbanUI ibanUI, PaymentCardUI paymentCardUI) {
        return new PayInPayOutFinancialDataUI(configuration, hostName, isSandbox, ibanUI, paymentCardUI);
    }
}
