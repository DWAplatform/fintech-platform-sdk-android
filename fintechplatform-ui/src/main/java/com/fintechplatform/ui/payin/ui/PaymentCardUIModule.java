package com.fintechplatform.ui.payin.ui;

import com.fintechplatform.ui.card.ui.PaymentCardUI;

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
