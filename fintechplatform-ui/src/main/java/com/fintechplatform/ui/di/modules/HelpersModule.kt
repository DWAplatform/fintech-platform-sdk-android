package com.fintechplatform.ui.di.modules

import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.money.FeeHelperModule
import com.fintechplatform.ui.money.MoneyHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    AlertHelpersModule::class,
    BalanceHelperModule::class,
    FeeHelperModule::class,
    ApiHelpersModule::class
])
class HelpersModule {
    @Provides
    @Singleton
    fun providesMoneyHelper(): MoneyHelper {
        return MoneyHelper()
    }
}