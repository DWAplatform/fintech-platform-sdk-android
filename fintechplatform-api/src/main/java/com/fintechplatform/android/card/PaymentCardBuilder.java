package com.fintechplatform.android.card;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.card.api.PaymentCardAPIModule;
import com.fintechplatform.android.net.NetModule;

public class PaymentCardBuilder {

    public PaymentCardAPIComponent createPaymentCardAPI(Context context, String hostname, boolean sandbox) {
        return DaggerPaymentCardAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostname))
                .paymentCardAPIModule(new PaymentCardAPIModule(hostname, sandbox))
                .build();
    }
}
