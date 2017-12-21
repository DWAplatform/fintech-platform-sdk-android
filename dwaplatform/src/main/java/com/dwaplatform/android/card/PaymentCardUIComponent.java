package com.dwaplatform.android.card;

import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.card.ui.PaymentCardUI;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 14/12/17.
 */
@Singleton
@Component (modules = {PaymentCardUIModule.class})
public interface PaymentCardUIComponent {
    PaymentCardUI getPaymentCardUI();
}
