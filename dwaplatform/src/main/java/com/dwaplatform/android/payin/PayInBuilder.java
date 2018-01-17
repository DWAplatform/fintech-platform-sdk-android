package com.dwaplatform.android.payin;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.card.ui.PaymentCardUIModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.payin.api.PayInAPIModule;
import com.dwaplatform.android.payin.ui.PayInUIModule;

/**
 * Created by tcappellari on 08/12/2017.
 */

public class PayInBuilder {

    public PayInAPIComponent createPayInAPIComponent(String hostName, Context context) {
        return DaggerPayInAPIComponent.builder()
                .payInAPIModule(new PayInAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

    public PayInUIComponent createPayInUIComponent(String hostName, boolean sandbox, DataAccount configuration) {
        return DaggerPayInUIComponent.builder()
                .payInUIModule(new PayInUIModule(hostName,
                        configuration))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, sandbox, configuration))
                .build();
    }
}