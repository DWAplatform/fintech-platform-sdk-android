package com.fintechplatform.ui.iban.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class IBANActivityModule {
    @ContributesAndroidInjector(modules = [IBANFragmentModule::class])
    abstract fun contributeIBANActivity(): IBANActivity
}