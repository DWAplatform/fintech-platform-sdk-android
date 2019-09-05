package com.fintechplatform.ui.secure3d.di

import com.fintechplatform.ui.secure3d.Secure3DContract
import com.fintechplatform.ui.secure3d.Secure3DPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
open class Secure3DPresenterModule(private val view: Secure3DContract.View) {

    @Singleton
    @Provides
    internal fun providesSecure3DPresenter(): Secure3DContract.Presenter = Secure3DPresenter(view)
    
}