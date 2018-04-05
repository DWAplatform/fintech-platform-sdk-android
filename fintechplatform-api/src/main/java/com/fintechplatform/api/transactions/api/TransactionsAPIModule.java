package com.fintechplatform.api.transactions.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TransactionsAPIModule {
    private String hostname;

    public TransactionsAPIModule(String hostname) {
        this.hostname = hostname;
    }

    @Provides
    @Singleton
    TransactionsAPI providesTransactionsAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new TransactionsAPI(hostname, queue, requestProvider, log, netHelper);
    }


}
