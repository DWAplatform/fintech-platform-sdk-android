package com.fintechplatform.ui.di.modules

import androidx.lifecycle.ViewModelProvider
import com.fintechplatform.ui.di.FintechPlatformViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
/*
    @Binds
    @IntoMap
    @ViewModelKey(TopUpPresenter::class)
    abstract fun bindTopUpViewModel(topUpViewModel: TopUpPresenter): ViewModel
*/
    @Binds
    abstract fun bindViewModelFactory(factory: FintechPlatformViewModelFactory): ViewModelProvider.Factory
}