package com.dwaplatform.android.iban.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IbanUIModule {

    @Provides
    @Singleton
    IbanUI providesIbanUI(){
        return new IbanUI();
    }
}
