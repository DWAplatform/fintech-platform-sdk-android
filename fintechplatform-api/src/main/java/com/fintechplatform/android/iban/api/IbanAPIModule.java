package com.fintechplatform.android.iban.api;

import com.fintechplatform.android.log.Log;
import com.fintechplatform.android.net.IRequestProvider;
import com.fintechplatform.android.net.IRequestQueue;
import com.fintechplatform.android.net.NetHelper;

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
    IbanAPI providesIbanAPI(IRequestQueue queue, IRequestProvider requestProvider, NetHelper netHelper) {
        return new IbanAPI(hostname, queue, requestProvider, netHelper);
    }
}
