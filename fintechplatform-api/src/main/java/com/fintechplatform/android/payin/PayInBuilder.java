package com.fintechplatform.android.payin;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.payin.api.PayInAPIModule;

public class PayInBuilder {

    public PayInAPIComponent createPayInAPIComponent(String hostName, Context context) {
        return DaggerPayInAPIComponent.builder()
                .payInAPIModule(new PayInAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

}