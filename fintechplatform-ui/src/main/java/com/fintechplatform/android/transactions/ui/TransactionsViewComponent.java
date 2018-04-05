package com.fintechplatform.android.transactions.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.money.MoneyHelperModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.transactions.api.TransactionsAPIModule;
import com.fintechplatform.android.transactions.db.TransactionPersistanceDBModule;
import com.fintechplatform.android.transactions.models.TransactionsManagerModule;

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
