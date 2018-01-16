package com.dwaplatform.android.payin.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
    PayInAPI providePayInAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log) {
        return new PayInAPI(hostName, queue, requestProvider, log);
    }
}
