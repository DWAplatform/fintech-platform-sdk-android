package com.fintechplatform.android.transactions.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.api.NetHelper;
import com.fintechplatform.android.log.Log;

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
