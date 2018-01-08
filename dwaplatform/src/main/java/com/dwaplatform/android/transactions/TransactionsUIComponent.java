package com.dwaplatform.android.transactions;

import com.dwaplatform.android.transactions.ui.TransactionsUI;
import com.dwaplatform.android.transactions.ui.TransactionsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = { TransactionsUIModule.class })
public interface TransactionsUIComponent {
    TransactionsUI getTransactiosUI();
}
