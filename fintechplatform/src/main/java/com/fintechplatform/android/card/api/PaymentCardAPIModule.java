package com.fintechplatform.android.card.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.api.NetHelper;
import com.fintechplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 21/12/17.
 */
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
    PaymentCardAPI providesPaymentCardAPI(IRequestQueue queue, IRequestProvider provider, Log log, NetHelper netHelper) {
        return new PaymentCardAPI(hostname, queue, provider, log, sandbox, netHelper);
    }
}
