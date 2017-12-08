package com.dwaplatform.android.api;

import com.dwaplatform.android.account.balance.api.BalanceAPI;
import com.dwaplatform.android.account.balance.api.BalanceAPIModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.payin.api.PayInAPI;
import com.dwaplatform.android.payin.api.PayInAPIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 08/12/17.
 */
@Singleton
@Component(modules= {
        BalanceAPIModule.class,
        PayInAPIModule.class,
        LogModule.class,
        NetModule.class
})
public interface NetComponent {
    BalanceAPI getBalanceApi();
    PayInAPI getPayInApi();
}
