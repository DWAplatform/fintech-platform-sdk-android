package com.fintechplatform.ui.transactions.ui;

import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.ui.transactions.ui.detail.ui.TransactionDetailUI;

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
