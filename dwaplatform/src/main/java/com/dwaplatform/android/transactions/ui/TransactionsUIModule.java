package com.dwaplatform.android.transactions.ui;

import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.transactions.ui.detail.ui.TransactionDetailUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionsUIModule {

    private String hostname;
    private DataAccount configuration;

    public TransactionsUIModule(String hostname, DataAccount configuration) {
        this.hostname = hostname;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    TransactionsUI providesTransactionsUI(TransactionDetailUI detailUI) {
        return new TransactionsUI(hostname, configuration, detailUI);
    }
}