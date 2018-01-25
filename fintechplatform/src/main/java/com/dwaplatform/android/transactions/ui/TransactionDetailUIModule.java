package com.dwaplatform.android.transactions.ui;

import com.dwaplatform.android.transactions.ui.detail.ui.TransactionDetailUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionDetailUIModule {

    private TransactionDetailUI detailUI;

    public TransactionDetailUIModule(TransactionDetailUI detailUI) {
        this.detailUI = detailUI;
    }

    @Provides
    @Singleton
    TransactionDetailUI providesTransactionDetailUIInstance() {
        return detailUI;
    }
}
