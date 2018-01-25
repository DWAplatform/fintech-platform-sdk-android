package com.dwaplatform.android.iban.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.api.NetHelper;
import com.dwaplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IbanAPIModule {

    private String hostname;

    public IbanAPIModule(String hostname) {
        this.hostname = hostname;
    }

    @Provides
    @Singleton
    IbanAPI providesIbanAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new IbanAPI(hostname, queue, requestProvider, log, netHelper);
    }
}
