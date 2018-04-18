package com.fintechplatform.api.pagopa.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 18/04/18.
 */
@Module
public class PagoPaAPIModule {

    private String hostName;

    public PagoPaAPIModule(String hostName) {
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    PagoPaAPI providesPagoPAApiModule(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new PagoPaAPI(hostName, queue, requestProvider, log, netHelper);
    }
}
