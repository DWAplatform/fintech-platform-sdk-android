package com.fintechplatform.ui.transactions.di

import com.fintechplatform.ui.transactions.ui.itemview.TransactionItemContract
import com.fintechplatform.ui.transactions.ui.itemview.TransactionItemPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class TransactionItemPresenterModule(private val view: TransactionItemContract.View) {

    @Provides
    @Singleton
    internal fun provideTransactionItemContractView(): TransactionItemContract.View {
        return view
    }

    @Provides
    @Singleton
    internal fun provideTransactionItemPresenter(): TransactionItemContract.Presenter {
        return TransactionItemPresenter(view)
    }
}
