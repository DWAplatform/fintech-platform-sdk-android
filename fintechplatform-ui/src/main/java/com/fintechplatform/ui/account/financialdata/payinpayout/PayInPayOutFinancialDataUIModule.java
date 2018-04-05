package com.fintechplatform.ui.account.financialdata.payinpayout;

import com.fintechplatform.ui.card.ui.PaymentCardUI;
import com.fintechplatform.ui.iban.ui.IbanUI;
import com.fintechplatform.ui.models.DataAccount;

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
