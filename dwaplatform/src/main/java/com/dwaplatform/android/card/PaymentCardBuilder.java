package com.dwaplatform.android.card;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.api.PaymentCardAPIModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.models.DataAccount;

/**
 * Created by ingrid on 14/12/17.
 */

public class PaymentCardBuilder {

    public PaymentCardAPIComponent createPaymentCardAPI(Context context, String hostname, boolean sandbox) {
        return DaggerPaymentCardAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .paymentCardAPIModule(new PaymentCardAPIModule(hostname, sandbox))
                .build();
    }

    public PaymentCardUIComponent createPaymentCardUI(String hostname, boolean sandbox, DataAccount configuration) {
        return DaggerPaymentCardUIComponent.builder()
                .paymentCardUIModule(new PaymentCardUIModule(hostname, sandbox, configuration))
                .build();
    }
}
