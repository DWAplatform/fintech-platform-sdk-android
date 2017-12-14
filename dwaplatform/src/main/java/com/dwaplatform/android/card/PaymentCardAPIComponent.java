package com.dwaplatform.android.card;

import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.CardRestApiModule;
import com.dwaplatform.android.card.api.PaymentCardAPI;
import com.dwaplatform.android.log.LogModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ingrid on 14/12/17.
 */
@Singleton
@Component (modules = {
                        NetModule.class,
                        CardRestApiModule.class,
                        LogModule.class
})
public interface PaymentCardAPIComponent {
    PaymentCardAPI getPaymentCardAPI();
}
