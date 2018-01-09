package com.dwaplatform.android.account.balance;

import com.dwaplatform.android.account.balance.api.BalanceAPI;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.account.balance.helpers.BalanceHelper;
import com.dwaplatform.android.account.balance.helpers.BalanceHelperModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.log.LogModule;

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
        LogModule.class,
        KeyChainModule.class

})
public interface BalanceHelperComponent {

    BalanceHelper getBalanceHelper();
}


