package com.fintechplatform.api.iban.api;

import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

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
