package com.dwaplatform.android.card.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.log.Log;

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
    PaymentCardAPI providesPaymentCardAPI(IRequestQueue queue, IRequestProvider provider, Log log) {
        return new PaymentCardAPI(hostname, queue, provider, log, sandbox);
    }
}
