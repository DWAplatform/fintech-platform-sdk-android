package com.fintechplatform.ui.account.balance;

import com.fintechplatform.api.account.balance.api.BalanceAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper;
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule;

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


