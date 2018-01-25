package com.dwaplatform.android.account.financialdata.ui;

import com.dwaplatform.android.account.payinpayoutfinancialdata.ui.PayInPayOutFinancialDataUI;
import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.iban.ui.IbanUI;
import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FinancialDataUIModule {
    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;

    public FinancialDataUIModule(DataAccount configuration, String hostName, boolean isSandbox) {
        this.configuration = configuration;
        this.hostName = hostName;
        this.isSandbox = isSandbox;
    }

    @Provides
    @Singleton
    FinancialDataUI providesFinancialDataUI(IbanUI ibanUI, PaymentCardUI paymentCardUI, PayInPayOutFinancialDataUI payInPayOutFinancialDataUI) {
        return new FinancialDataUI(configuration, hostName, isSandbox, ibanUI, paymentCardUI, payInPayOutFinancialDataUI);
    }

}
