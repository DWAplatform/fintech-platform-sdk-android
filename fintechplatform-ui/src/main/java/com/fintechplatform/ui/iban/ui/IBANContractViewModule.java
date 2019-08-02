package com.fintechplatform.ui.iban.ui;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class IBANContractViewModule {
    @Binds
    abstract IBANContract.View contributeIBANFragment(IBANActivity activity);
}
