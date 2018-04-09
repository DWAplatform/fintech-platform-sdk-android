package com.fintechplatform.api.cashout.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CashOutAPIModule {

    private String hostName;

    public CashOutAPIModule(String hostName) {
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    CashOutAPI providePayInAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new CashOutAPI(hostName, queue, requestProvider, log, netHelper);
    }
}
