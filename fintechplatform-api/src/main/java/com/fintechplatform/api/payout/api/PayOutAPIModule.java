package com.fintechplatform.api.payout.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PayOutAPIModule {

    private String hostName;

    public PayOutAPIModule(String hostName) {
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    PayOutAPI providePayInAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new PayOutAPI(hostName, queue, requestProvider, log, netHelper);
    }
}
