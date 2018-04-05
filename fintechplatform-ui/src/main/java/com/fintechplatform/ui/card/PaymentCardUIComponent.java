package com.fintechplatform.ui.card;

import com.fintechplatform.ui.card.ui.PaymentCardUI;
import com.fintechplatform.ui.card.ui.PaymentCardUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {PaymentCardUIModule.class})
public interface PaymentCardUIComponent {
    PaymentCardUI getPaymentCardUI();
}
