package com.fintechplatform.api.payin;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.payin.api.PayInAPIModule;

public class PayInAPIBuilder {

    public PAyInAPIComponent createPayInAPIComponent(String hostName, Context context) {
        return DaggerPAyInAPIComponent.builder()
                .payInAPIModule(new PayInAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

}