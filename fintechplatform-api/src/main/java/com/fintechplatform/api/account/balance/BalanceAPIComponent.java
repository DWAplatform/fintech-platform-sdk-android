package com.fintechplatform.api.account.balance;

import com.fintechplatform.api.account.balance.api.BalanceAPI;
import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules= {
        NetModule.class,
        BalanceAPIModule.class,
        LogModule.class
})
public interface BalanceAPIComponent {
    BalanceAPI getBalanceAPI();
}

