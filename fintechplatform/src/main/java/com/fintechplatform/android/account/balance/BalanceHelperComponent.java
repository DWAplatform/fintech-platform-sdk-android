package com.fintechplatform.android.account.balance;

import com.fintechplatform.android.account.balance.api.BalanceAPIModule;
import com.fintechplatform.android.account.balance.helpers.BalanceHelper;
import com.fintechplatform.android.account.balance.helpers.BalanceHelperModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.log.LogModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */


@Singleton
@Component(modules= {
        NetModule.class,
        BalanceAPIModule.class,
        BalanceHelperModule.class,
        LogModule.class

})
public interface BalanceHelperComponent {

    BalanceHelper getBalanceHelper();
}


