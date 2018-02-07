package com.fintechplatform.android.transfer.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.api.NetHelper;
import com.fintechplatform.android.log.Log;

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
