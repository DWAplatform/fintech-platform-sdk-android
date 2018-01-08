package com.dwaplatform.android.transactions.ui.transactionItemView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionItemUIModule {

    @Provides
    @Singleton
    TransactionItemUI providesTransactionItemUI() {
        return new TransactionItemUI();
    }
}
