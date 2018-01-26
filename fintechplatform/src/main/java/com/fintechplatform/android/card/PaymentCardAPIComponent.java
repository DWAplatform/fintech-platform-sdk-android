package com.fintechplatform.android.card;

import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.card.api.PaymentCardAPI;
import com.fintechplatform.android.card.api.PaymentCardAPIModule;
import com.fintechplatform.android.log.LogModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 14/12/17.
 */
@Singleton
@Component (modules = {
                        NetModule.class,
                        PaymentCardAPIModule.class,
                        LogModule.class
})
public interface PaymentCardAPIComponent {
    PaymentCardAPI getPaymentCardAPI();
}