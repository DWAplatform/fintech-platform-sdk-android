package com.fintechplatform.ui.transactions.di

import com.fintechplatform.api.transactions.api.TransactionsAPI
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.transactions.db.TransactionPersistanceDB
import com.fintechplatform.ui.transactions.ui.TransactionsContract
import com.fintechplatform.ui.transactions.ui.TransactionsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TransactionsPresenterModule(private val view: TransactionsContract.View, private val configuration: DataAccount) {

    @Provides
    @Singleton
    internal fun providesTransactionsPresenter(api: TransactionsAPI,
                                               transactionsPersistance: TransactionPersistanceDB): TransactionsContract.Presenter {
        return TransactionsPresenter(view, api, configuration, transactionsPersistance)
    }
}
