package com.dwaplatform.android.iban.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IbanUIModule {

    private IbanUI ibanUI;

    public IbanUIModule(IbanUI ibanUI) {
        this.ibanUI = ibanUI;
    }

    @Provides
    @Singleton
    IbanUI providesIbanUI(){
        return ibanUI;
    }
}
