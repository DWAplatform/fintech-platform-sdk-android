package com.fintechplatform.ui.secure3d.di

import com.fintechplatform.ui.secure3d.Secure3DFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Secure3DPresenterModule::class])
interface Secure3DViewComponent {
    fun inject(fragment: Secure3DFragment)
}