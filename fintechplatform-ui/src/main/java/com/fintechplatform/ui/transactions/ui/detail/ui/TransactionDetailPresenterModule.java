package com.fintechplatform.ui.transactions.ui.detail.ui;

import com.fintechplatform.ui.transactions.models.TransactionDetailHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionDetailPresenterModule {

    private TransactionDetailContract.View view;

    public TransactionDetailPresenterModule(TransactionDetailContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    TransactionDetailContract.Presenter providesTransactionDetailPresenter(TransactionDetailHelper helper) {
        return new TransactionDetailPresenter(view, helper);
    }

    @Provides
    @Singleton
    TransactionDetailHelper providesTransactionDetailHelper() {
        return new TransactionDetailHelper();
    }
}
