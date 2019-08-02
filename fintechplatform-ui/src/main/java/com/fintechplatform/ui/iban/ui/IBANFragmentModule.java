package com.fintechplatform.ui.iban.ui;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class IBANFragmentModule {
    @ContributesAndroidInjector(modules = {
            //IBANContractViewSubComponent.class
    })
    abstract IBANFragment contributeIBANFragment();
}
