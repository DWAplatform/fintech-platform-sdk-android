package com.fintechplatform.android.transactions.ui.itemview;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionItemPresenterModule {
    private final TransactionItemContract.View view;

    public TransactionItemPresenterModule(TransactionItemContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    TransactionItemContract.View provideTransactionItemContractView() {
        return view;
    }

    @Provides
    @Singleton
    TransactionItemContract.Presenter provideTransactionItemPresenter() {
        return new TransactionItemPresenter(view);
    }
}
