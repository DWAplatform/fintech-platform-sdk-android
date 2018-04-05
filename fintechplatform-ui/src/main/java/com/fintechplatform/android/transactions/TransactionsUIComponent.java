package com.fintechplatform.android.transactions;

import com.fintechplatform.android.transactions.ui.TransactionsUI;
import com.fintechplatform.android.transactions.ui.TransactionsUIModule;
import com.fintechplatform.android.transactions.ui.detail.ui.TransactionDetailUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        TransactionsUIModule.class,
        TransactionDetailUIModule.class

})
public interface TransactionsUIComponent {
    TransactionsUI getTransactiosUI();
}
