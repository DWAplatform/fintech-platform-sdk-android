package com.fintechplatform.android.payin;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.card.ui.PaymentCardUIModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.payin.api.PayInAPIModule;
import com.fintechplatform.android.payin.ui.PayInUIModule;

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
                        configuration, sandbox))
                .paymentCardUIModule(new PaymentCardUIModule(hostName, sandbox, configuration))
                .build();
    }
}