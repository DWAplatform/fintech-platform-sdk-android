package com.fintechplatform.api.cashout;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.cashout.api.CashOutAPI;
import com.fintechplatform.api.cashout.api.CashOutAPIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        CashOutAPIModule.class,
        NetModule.class,
        LogModule.class
})
public interface CashOutAPIComponent {
    CashOutAPI getCashOutAPI();
}
