package com.fintechplatform.android.card;

import com.fintechplatform.android.card.ui.PaymentCardUI;
import com.fintechplatform.android.card.ui.PaymentCardUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {PaymentCardUIModule.class})
public interface PaymentCardUIComponent {
    PaymentCardUI getPaymentCardUI();
}
