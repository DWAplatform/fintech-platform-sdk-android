package com.fintechplatform.android.payin.ui;

import com.fintechplatform.android.card.ui.PaymentCardUI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentCardUIModule {
    private PaymentCardUI paymentCardUI;

    public PaymentCardUIModule(PaymentCardUI paymentCardUI) {
        this.paymentCardUI = paymentCardUI;
    }

    @Provides
    @Singleton
    PaymentCardUI providePaymentCardUI() {
        return paymentCardUI;
    }
}
