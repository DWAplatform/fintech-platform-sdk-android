package com.dwaplatform.android.card.client.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.card.helpers.JSONHelper;
import com.dwaplatform.android.card.helpers.PaymentCardHelper;
import com.dwaplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 14/12/17.
 */
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
