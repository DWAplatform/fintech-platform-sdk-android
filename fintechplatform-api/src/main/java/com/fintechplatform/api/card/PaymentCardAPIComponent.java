package com.fintechplatform.api.card;

import com.fintechplatform.api.card.api.PaymentCardAPI;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;

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
