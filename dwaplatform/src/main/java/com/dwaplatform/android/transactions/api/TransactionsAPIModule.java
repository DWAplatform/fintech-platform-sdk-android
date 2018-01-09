package com.dwaplatform.android.transactions.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.log.Log;

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
    TransactionsAPI providesTransactionsAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log) {
        return new TransactionsAPI(hostname, queue, requestProvider, log);
    }
}
