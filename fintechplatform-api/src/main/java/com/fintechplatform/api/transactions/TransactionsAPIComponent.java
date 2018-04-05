package com.fintechplatform.api.transactions;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.transactions.api.TransactionsAPI;
import com.fintechplatform.api.transactions.api.TransactionsAPIModule;

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
