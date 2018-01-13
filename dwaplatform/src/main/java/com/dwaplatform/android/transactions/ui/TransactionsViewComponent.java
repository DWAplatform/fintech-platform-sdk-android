package com.dwaplatform.android.transactions.ui;

import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.money.MoneyHelperModule;
import com.dwaplatform.android.transactions.api.TransactionsAPIModule;
import com.dwaplatform.android.transactions.db.TransactionPersistanceDBModule;
import com.dwaplatform.android.transactions.models.TransactionsManagerModule;

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
        TransactionsManagerModule.class,
        TransactionDetailUIModule.class
})
public interface TransactionsViewComponent {
    void inject(TransactionsActivity activity);
}
