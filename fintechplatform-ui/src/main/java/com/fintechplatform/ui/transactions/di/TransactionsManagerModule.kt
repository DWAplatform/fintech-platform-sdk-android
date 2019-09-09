package com.fintechplatform.ui.transactions.di

import com.fintechplatform.ui.transactions.models.TransactionsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TransactionsManagerModule {
    @Provides
    @Singleton
    internal fun providesTransactionsManager(): TransactionsManager {
        return TransactionsManager()
    }
}
