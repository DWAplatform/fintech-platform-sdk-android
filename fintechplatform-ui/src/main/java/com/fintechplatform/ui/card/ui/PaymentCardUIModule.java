package com.fintechplatform.ui.card.ui;

import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
