package com.fintechplatform.android.payout.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.api.NetHelper;
import com.fintechplatform.android.log.Log;

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
