package com.fintechplatform.android.card.client.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.card.helpers.JSONHelper;
import com.fintechplatform.android.card.helpers.PaymentCardHelper;
import com.fintechplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ClientCardRestApiModule {

    String hostname;
    boolean sandbox;

    public ClientCardRestApiModule(String hostname, boolean sandbox) {
        this.hostname = hostname;
        this.sandbox = sandbox;
    }

    @Provides
    @Singleton
    ClientCardAPI providesCardAPI(ClientCardRestAPI cardrestapi, Log log, PaymentCardHelper cardhelper) {
        return new ClientCardAPI(cardrestapi, log, cardhelper);
    }

    @Singleton
    @Provides
    ClientCardRestAPI providesCardRestApi(IRequestQueue queue, IRequestProvider provider, JSONHelper jsonHelper) {
        return new ClientCardRestAPI(hostname, queue, provider, jsonHelper, sandbox);
    }

    @Singleton
    @Provides
    JSONHelper providesJsonHelper() {
        return new JSONHelper();
    }
}
