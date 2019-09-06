package com.fintechplatform.ui.account.financialdata.bank;

import com.fintechplatform.ui.models.DataAccount;

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
    BankFinancialDataUI providesFinancialDataUI() {
        return new BankFinancialDataUI(configuration, hostName, isSandbox);
    }

}
