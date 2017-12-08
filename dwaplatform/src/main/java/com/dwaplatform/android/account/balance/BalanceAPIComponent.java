package com.dwaplatform.android.account.balance;

import com.dwaplatform.android.account.balance.api.BalanceAPI;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.payin.api.PayInAPI;
import com.dwaplatform.android.payin.api.PayInAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Singleton
@Component(modules= {
        NetModule.class,
        BalanceAPIModule.class,
        LogModule.class,

})
interface BalanceAPIComponent {

    BalanceAPI getBalanceAPI();
}

