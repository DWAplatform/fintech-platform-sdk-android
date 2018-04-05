package com.fintechplatform.api.payout;


import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.payout.api.PayOutAPIModule;

public class PayOutAPIBuilder {

    public PayOutAPIComponent createPayOutAPI(Context context, String hostName) {
        return DaggerPayOutAPIComponent.builder()
                .payOutAPIModule(new PayOutAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

}
