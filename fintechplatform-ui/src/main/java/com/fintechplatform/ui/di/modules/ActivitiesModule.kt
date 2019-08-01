package com.fintechplatform.ui.di.modules

import com.fintechplatform.ui.cashin.TopUpActivity
import com.fintechplatform.ui.di.components.TopUpComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


@Module(subcomponents = [TopUpComponent::class])
abstract class ActivitiesModule {
    @Binds
    @IntoMap
    @ClassKey(TopUpActivity::class)
    abstract fun contributeTopUpActivity(factory: TopUpComponent.Factory): AndroidInjector.Factory<TopUpActivity>
}
