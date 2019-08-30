package com.fintechplatform.ui.cashin.ui;

import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CashInUIModule {

    private DataAccount configuration;
    private String hostName;
    private boolean isSandbox;

    public CashInUIModule(String hostName,
                          DataAccount configuration,
                          boolean isSandbox) {
        this.hostName = hostName;
        this.configuration = configuration;
        this.isSandbox = isSandbox;
    }


    @Provides
    @Singleton
    CashInUI providePayInUI() {
        return new CashInUI(hostName, configuration, isSandbox);
    }

}
