package com.dwaplatform.android.card.ui;

import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 14/12/17.
 */
@Module
public class PaymentCardUIModule {

    private String hostname;
    private boolean sandbox;
    private DataAccount dataAccount;

    public PaymentCardUIModule(String hostname, boolean sandbox, DataAccount dataAccount) {
        this.hostname = hostname;
        this.sandbox = sandbox;
        this.dataAccount = dataAccount;
    }

    @Provides
    @Singleton
    PaymentCardUI providesPaymentCardUI() {
        return new PaymentCardUI(hostname, sandbox, dataAccount);
    }
}
