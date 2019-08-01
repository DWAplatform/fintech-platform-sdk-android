package com.fintechplatform.ui.iban.ui;

import android.content.Context;

import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IbanUIModule {

    private String hostname;
    private DataAccount configuration;
    private Context context;

    public IbanUIModule(String hostname, DataAccount configuraiton, Context context) {
        this.hostname = hostname;
        this.configuration = configuraiton;
        this.context = context;
    }

    @Provides
    @Singleton
    IbanUI providesIbanUI(){
        return new IbanUI(hostname, configuration, context);
    }
}
