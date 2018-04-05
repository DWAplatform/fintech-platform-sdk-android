package com.fintechplatform.api.card.api;

import com.fintechplatform.api.card.helpers.PaymentCardHelper;
import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

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
    PaymentCardRestAPI providesPaymentCardRestAPI(IRequestQueue queue, IRequestProvider provider, Log log, NetHelper netHelper) {
        return new PaymentCardRestAPI(hostname, queue, provider, log, sandbox, netHelper);
    }

    @Provides
    @Singleton
    PaymentCardAPI providesPaymentCardAPI(PaymentCardRestAPI restAPI, Log log, PaymentCardHelper helper) {
        return new PaymentCardAPI(restAPI, log, helper);
    }
}
