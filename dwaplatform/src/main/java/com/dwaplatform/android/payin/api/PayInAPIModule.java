package com.dwaplatform.android.payin.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class PayInAPIModule {

    private String hostName;
    private String token;


    public PayInAPIModule(String hostName, String token) {
        this.hostName = hostName;
        this.token = token;
    }

    @Provides
    @Singleton
    PayInAPI providePayInAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log) {
        return new PayInAPI(hostName, token, queue, requestProvider, log);
    }
}
