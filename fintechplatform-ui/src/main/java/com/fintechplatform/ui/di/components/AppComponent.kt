package com.fintechplatform.ui.di.components

import android.app.Application
import com.fintechplatform.ui.di.modules.ActivitiesModule
import com.fintechplatform.ui.di.modules.AppModule
import com.fintechplatform.ui.di.modules.HelpersModule
import com.fintechplatform.ui.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            HelpersModule::class,
            ActivitiesModule::class,
            ViewModelModule::class
        ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: Application)
}