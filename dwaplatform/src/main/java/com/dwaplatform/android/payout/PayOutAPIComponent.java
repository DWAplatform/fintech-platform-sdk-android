package com.dwaplatform.android.payout;

import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.payout.api.PayOutAPI;
import com.dwaplatform.android.payout.api.PayOutAPIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        PayOutAPIModule.class,
        NetModule.class,
        LogModule.class
})
interface PayOutAPIComponent {
    PayOutAPI getPayOutAPI();
}
