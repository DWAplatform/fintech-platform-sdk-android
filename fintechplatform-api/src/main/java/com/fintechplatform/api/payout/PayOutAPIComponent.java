package com.fintechplatform.api.payout;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.payout.api.PayOutAPI;
import com.fintechplatform.api.payout.api.PayOutAPIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        PayOutAPIModule.class,
        NetModule.class,
        LogModule.class
})
public interface PayOutAPIComponent {
    PayOutAPI getPayOutAPI();
}
