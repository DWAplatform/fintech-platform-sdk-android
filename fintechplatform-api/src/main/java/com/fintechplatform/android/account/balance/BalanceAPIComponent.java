package com.fintechplatform.android.account.balance;

import com.fintechplatform.android.account.balance.api.BalanceAPI;
import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        NetModule.class,
        BalanceAPIModule.class,
        LogModule.class
})
public interface BalanceAPIComponent {
    BalanceAPI getBalanceAPI();
}

