package com.dwaplatform.android.card.api;

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
public class PaymentCardRestApiModule {

    String hostname;
    boolean sandbox;

    public PaymentCardRestApiModule(String hostname, boolean sandbox) {
        this.hostname = hostname;
        this.sandbox = sandbox;
    }

    @Provides
    @Singleton
    PaymentCardAPI providesCardAPI(PaymentCardRestAPI cardrestapi, Log log, PaymentCardHelper cardhelper) {
        return new PaymentCardAPI(cardrestapi, log, cardhelper);
    }

    @Singleton
    @Provides
    PaymentCardRestAPI providesCardRestApi(IRequestQueue queue, IRequestProvider provider, JSONHelper jsonHelper) {
        return new PaymentCardRestAPI(hostname, queue, provider, jsonHelper, sandbox);
    }

    @Singleton
    @Provides
    JSONHelper providesJsonHelper() {
        return new JSONHelper();
    }
}
