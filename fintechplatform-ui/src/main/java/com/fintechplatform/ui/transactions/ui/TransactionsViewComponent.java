package com.fintechplatform.ui.transactions.ui;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transactions.api.TransactionsAPIModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.money.MoneyHelperModule;
import com.fintechplatform.ui.transactions.db.TransactionPersistanceDBModule;
import com.fintechplatform.ui.transactions.models.TransactionsManagerModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        TransactionsPresenterModule.class,
        TransactionsAPIModule.class,
        NetModule.class,
        LogModule.class,
        TransactionPersistanceDBModule.class,
        MoneyHelperModule.class,
        AlertHelpersModule.class,
        TransactionsManagerModule.class,
        TransactionDetailUIModule.class
})
public interface TransactionsViewComponent {
    void inject(TransactionsActivity activity);
}
