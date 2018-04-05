package com.fintechplatform.api.transfer.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransferAPIModule {
    private String hostname;

    public TransferAPIModule(String hostname) {
        this.hostname = hostname;
    }

    @Provides
    @Singleton
    TransferAPI providesTransferAPI(IRequestQueue requestQueue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new TransferAPI(hostname, requestQueue, requestProvider, log, netHelper);
    }
}
