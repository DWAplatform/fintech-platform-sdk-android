package com.fintechplatform.android.account.financialdata.bank;

import com.fintechplatform.android.card.ui.PaymentCardUI;
import com.fintechplatform.android.iban.ui.IbanUI;
import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BankFinancialDataUIModule {
    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;

    public BankFinancialDataUIModule(DataAccount configuration, String hostName, boolean isSandbox) {
        this.configuration = configuration;
        this.hostName = hostName;
        this.isSandbox = isSandbox;
    }

    @Provides
    @Singleton
    BankFinancialDataUI providesFinancialDataUI(IbanUI ibanUI, PaymentCardUI paymentCardUI) {
        return new BankFinancialDataUI(configuration, hostName, isSandbox, ibanUI, paymentCardUI);
    }

}
