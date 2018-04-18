package com.fintechplatform.ui.transactions.db;

import com.fintechplatform.ui.money.MoneyHelper;
import com.fintechplatform.ui.transactions.models.TransactionDetailHelper;
import com.fintechplatform.ui.transactions.models.TransactionHelper;

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

    @Provides
    @Singleton
    TransactionDetailHelper providesTransactionDetailHelper() {
        return new TransactionDetailHelper();
    }
}
