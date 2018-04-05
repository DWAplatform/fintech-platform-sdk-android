package com.fintechplatform.android.card;

import com.fintechplatform.android.card.api.PaymentCardAPI;
import com.fintechplatform.android.card.api.PaymentCardAPIModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {NetModule.class,
                        PaymentCardAPIModule.class,
                        LogModule.class
})
public interface PaymentCardAPIComponent {
    PaymentCardAPI getPaymentCardAPI();
}
