package com.fintechplatform.ui.transactions.ui.detail.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionDetailUIModule {

    @Provides
    @Singleton
    TransactionDetailUI providesTransactionDetailUI() {
        return new TransactionDetailUI();
    }


}
