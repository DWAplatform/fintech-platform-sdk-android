package com.fintechplatform.android.payout;

import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.payout.api.PayOutAPI;
import com.fintechplatform.android.payout.api.PayOutAPIModule;

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
