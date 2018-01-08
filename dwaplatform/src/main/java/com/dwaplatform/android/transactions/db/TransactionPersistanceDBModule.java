package com.dwaplatform.android.transactions.db;

import com.dwaplatform.android.money.MoneyHelper;

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
