package com.fintechplatform.ui.transactions;

import com.fintechplatform.ui.money.MoneyHelperModule;
import com.fintechplatform.ui.transactions.db.TransactionPersistanceDB;
import com.fintechplatform.ui.transactions.db.TransactionPersistanceDBModule;

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
