package com.dwaplatform.android.payout.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.log.Log;

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
    PayOutAPI providePayInAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log) {
        return new PayOutAPI(hostName, queue, requestProvider, log);
    }
}
