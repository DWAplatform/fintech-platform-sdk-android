package com.fintechplatform.android.transactions.db;

import com.fintechplatform.android.money.MoneyHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionPersistanceDBModule {

    @Provides
    @Singleton
    TransactionPersistanceDB providesTransactionsPersistanceDB(TransactionDB db, TransactionHelper helper) {
        return new TransactionPersistanceDB(db, helper);
    }

    @Provides
    @Singleton
    TransactionDB providesTransactionDB() {
        return new TransactionDB();
    }

    @Provides
    @Singleton
    TransactionHelper providesTransactionHelper(MoneyHelper moneyHelper) {
        return new TransactionHelper(moneyHelper);
    }
}
