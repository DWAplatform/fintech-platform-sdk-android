package com.dwaplatform.android.card;

import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.PaymentCardAPI;
import com.dwaplatform.android.card.api.PaymentCardAPIModule;
import com.dwaplatform.android.card.client.api.ClientCardRestApiModule;
import com.dwaplatform.android.card.client.api.ClientCardAPI;
import com.dwaplatform.android.log.LogModule;

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
