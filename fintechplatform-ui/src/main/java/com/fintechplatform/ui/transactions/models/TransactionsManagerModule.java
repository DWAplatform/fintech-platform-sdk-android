package com.fintechplatform.ui.transactions.models;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionsManagerModule {
    @Provides
    @Singleton
    TransactionsManager providesTransactionsManager() {
        return new TransactionsManager();
    }
}
