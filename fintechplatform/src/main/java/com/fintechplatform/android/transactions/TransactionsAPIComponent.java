package com.fintechplatform.android.transactions;

import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.transactions.api.TransactionsAPI;
import com.fintechplatform.android.transactions.api.TransactionsAPIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {TransactionsAPIModule.class,
        LogModule.class,
        NetModule.class
})
public interface TransactionsAPIComponent {
    TransactionsAPI getTransactionsAPI();
}
