package com.fintechplatform.ui.iban.ui;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
@Module
public abstract class IBANActivityModule {
    @ContributesAndroidInjector
    abstract IBANActivity contributeIBANActivity();
}
