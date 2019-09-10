package com.fintechplatform.ui.transactions.di

import com.fintechplatform.ui.money.MoneyHelperModule
import com.fintechplatform.ui.transactions.db.TransactionPersistanceDB
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TransactionPersistanceDBModule::class, MoneyHelperModule::class])
interface TransactionsHelperComponent {
    val transactionsHelper: TransactionPersistanceDB
}