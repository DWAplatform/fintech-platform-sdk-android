package com.fintechplatform.ui.iban.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class IBANFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeIBANFragment(): IBANFragment
}