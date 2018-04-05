package com.fintechplatform.api.card;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.card.api.PaymentCardAPIModule;
import com.fintechplatform.api.net.NetModule;

public class PaymentCardAPIBuilder {

    public PaymentCardAPIComponent createPaymentCardAPI(Context context, String hostname, boolean sandbox) {
        return DaggerPaymentCardAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostname))
                .paymentCardAPIModule(new PaymentCardAPIModule(hostname, sandbox))
                .build();
    }
}
