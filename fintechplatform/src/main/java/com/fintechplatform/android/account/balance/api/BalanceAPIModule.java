package com.fintechplatform.android.account.balance.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class BalanceAPIModule {

    private String hostName;


    public BalanceAPIModule(String hostName) {
        this.hostName = hostName;
    }
//
//    @Provides
//    @Singleton
//    IRequestProvider provideJsonArrayRequest() {
//        return new VolleyRequestProvider();
//    }
//
//    @Provides
//    @Singleton
//    IRequestQueue provideVolleyRequestQueue() {
//        return new VolleyRequestQueueProvider(requestQueue);
//    }


    @Provides
    @Singleton
    BalanceAPI provideBalanceAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log) {
        return new BalanceAPI(hostName, queue, requestProvider, log);
    }
}

