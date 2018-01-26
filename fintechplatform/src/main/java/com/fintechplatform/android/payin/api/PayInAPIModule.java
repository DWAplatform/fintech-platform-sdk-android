package com.fintechplatform.android.payin.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.api.NetHelper;
import com.fintechplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class PayInAPIModule {

    private String hostName;


    public PayInAPIModule(String hostName) {
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    PayInAPI providePayInAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new PayInAPI(hostName, queue, requestProvider, log, netHelper);
    }
}