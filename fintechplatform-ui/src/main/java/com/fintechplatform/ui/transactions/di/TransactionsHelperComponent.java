package com.fintechplatform.ui.transactions.di;

import com.fintechplatform.ui.money.MoneyHelperModule;
import com.fintechplatform.ui.transactions.db.TransactionPersistanceDB;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 05/04/18.
 */
@Singleton
@Component (modules = {
        TransactionPersistanceDBModule.class,
        MoneyHelperModule.class
})
public interface TransactionsHelperComponent {
    TransactionPersistanceDB getTransactionsHelper();
}
