package com.dwaplatform.android.payin;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.payin.api.PayInAPIModule;
import com.dwaplatform.android.payin.models.PayInConfiguration;
import com.dwaplatform.android.payin.ui.PayInUIModule;

/**
 * Created by tcappellari on 08/12/2017.
 */

public class PayInBuilder {

    public PayInAPIComponent createPayInAPIComponent(String hostName, String token, Context context) {
        return DaggerPayInAPIComponent.builder()
                .payInAPIModule(new PayInAPIModule(hostName,
                                token))
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .build();
    }

    public PayInUIComponent createPayInUIComponent(String hostName, String token, boolean sandbox, PayInConfiguration configuration) {
        return DaggerPayInUIComponent.builder()
                .payInUIModule(new PayInUIModule(hostName,
                        token,
                        configuration))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, token, sandbox))
                .build();
    }
}