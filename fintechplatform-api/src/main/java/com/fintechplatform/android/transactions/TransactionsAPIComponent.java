package com.fintechplatform.android.transactions;

import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.money.MoneyHelperModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.transactions.api.TransactionsAPI;
import com.fintechplatform.android.transactions.api.TransactionsAPIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {TransactionsAPIModule.class,
        LogModule.class,
        NetModule.class,
        MoneyHelperModule.class
})
public interface TransactionsAPIComponent {
    TransactionsAPI getTransactionsAPI();
}
