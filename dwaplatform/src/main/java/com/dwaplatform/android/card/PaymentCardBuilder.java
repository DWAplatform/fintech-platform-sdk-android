package com.dwaplatform.android.card;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.CardRestApiModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;

/**
 * Created by ingrid on 14/12/17.
 */

public class PaymentCardBuilder {

    public PaymentCardAPIComponent buildPaymentCardAPI(Context context, String hostname, String token, boolean sandbox) {
        return DaggerPaymentCardAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .cardRestApiModule(new CardRestApiModule(hostname, token, sandbox))
                .build();
    }

    public PaymentCardUIComponent createPaymentCardUI(String hostname, String token, boolean sandbox) {
        return DaggerPaymentCardUIComponent.builder()
                .paymentCardUIModule(new PaymentCardUIModule(hostname, token, sandbox))
                .build();
    }
}
