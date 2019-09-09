package com.fintechplatform.ui.transactions.ui.detail.di

import com.fintechplatform.ui.transactions.models.TransactionDetailHelper
import com.fintechplatform.ui.transactions.ui.detail.TransactionDetailContract
import com.fintechplatform.ui.transactions.ui.detail.TransactionDetailPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TransactionDetailPresenterModule(private val view: TransactionDetailContract.View) {

    @Provides
    @Singleton
    internal fun providesTransactionDetailPresenter(helper: TransactionDetailHelper): TransactionDetailContract.Presenter {
        return TransactionDetailPresenter(view, helper)
    }

    @Provides
    @Singleton
    internal fun providesTransactionDetailHelper(): TransactionDetailHelper {
        return TransactionDetailHelper()
    }
}
