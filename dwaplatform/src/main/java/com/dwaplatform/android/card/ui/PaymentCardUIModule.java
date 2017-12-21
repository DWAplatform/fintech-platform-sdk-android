package com.dwaplatform.android.card.ui;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 14/12/17.
 */
@Module
public class PaymentCardUIModule {

    String hostname;
    boolean sandbox;

    public PaymentCardUIModule(String hostname, boolean sandbox) {
        this.hostname = hostname;
        this.sandbox = sandbox;
    }

    @Provides
    @Singleton
    PaymentCardUI providesPaymentCardUI() {
        return new PaymentCardUI(hostname, sandbox);
    }
}
