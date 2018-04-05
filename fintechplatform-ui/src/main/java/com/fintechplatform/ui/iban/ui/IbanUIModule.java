package com.fintechplatform.ui.iban.ui;

import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IbanUIModule {

    private String hostname;
    private DataAccount configuration;

    public IbanUIModule(String hostname, DataAccount configuraiton) {
        this.hostname = hostname;
        this.configuration = configuraiton;
    }

    @Provides
    @Singleton
    IbanUI providesIbanUI(){
        return new IbanUI(hostname, configuration);
    }
}
