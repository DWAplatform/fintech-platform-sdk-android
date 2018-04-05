package com.fintechplatform.ui.payout.ui;

import com.fintechplatform.ui.iban.ui.IbanUI;

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
    IbanUI providesIbanUIInstance(){
        return ibanUI;
    }
}
