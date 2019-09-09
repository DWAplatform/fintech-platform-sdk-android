package com.fintechplatform.ui.transactions.di

import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.transactions.api.TransactionsAPIModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.money.MoneyHelperModule
import com.fintechplatform.ui.transactions.ui.TransactionsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TransactionsPresenterModule::class,
    TransactionsAPIModule::class,
    NetModule::class,
    LogModule::class,
    TransactionPersistanceDBModule::class,
    MoneyHelperModule::class,
    AlertHelpersModule::class,
    TransactionsManagerModule::class
])

interface TransactionsViewComponent {
    fun inject(fragment: TransactionsFragment)
}
