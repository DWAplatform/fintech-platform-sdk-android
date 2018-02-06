package com.fintechplatform.android.card.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.api.NetHelper;
import com.fintechplatform.android.card.helpers.JSONHelper;
import com.fintechplatform.android.card.helpers.PaymentCardHelper;
import com.fintechplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentCardAPIModule {

    String hostname;
    boolean sandbox;

    public PaymentCardAPIModule(String hostname, boolean sandbox) {
        this.hostname = hostname;
        this.sandbox = sandbox;
    }

    @Provides
    @Singleton
    PaymentCardRestAPI providesPaymentCardRestAPI(IRequestQueue queue, IRequestProvider provider, Log log, JSONHelper jsonHelper, NetHelper netHelper) {
        return new PaymentCardRestAPI(hostname, queue, provider, log, jsonHelper, sandbox, netHelper);
    }

    @Provides
    @Singleton
    PaymentCardAPI providesPaymentCardAPI(PaymentCardRestAPI restAPI, Log log, PaymentCardHelper helper) {
        return new PaymentCardAPI(restAPI, log, helper);
    }

    @Singleton
    @Provides
    JSONHelper providesJsonHelper() {
        return new JSONHelper();
    }
}
