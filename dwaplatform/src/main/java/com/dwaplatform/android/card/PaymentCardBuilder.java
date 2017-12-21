package com.dwaplatform.android.card;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.card.api.PaymentCardRestApiModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;

/**
 * Created by ingrid on 14/12/17.
 */

public class PaymentCardBuilder {

    public PaymentCardAPIComponent createPaymentCardAPI(Context context, String hostname, boolean sandbox) {
        return DaggerPaymentCardAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .paymentCardRestApiModule(new PaymentCardRestApiModule(hostname, sandbox))
                .build();
    }

    public PaymentCardUIComponent createPaymentCardUI(String hostname, boolean sandbox) {
        return DaggerPaymentCardUIComponent.builder()
                .paymentCardUIModule(new PaymentCardUIModule(hostname, sandbox))
                .build();
    }
}
