package com.fintechplatform.android.payout;


import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.payout.api.PayOutAPIModule;

public class PayOutBuilder {

    public PayOutAPIComponent createPayOutAPI(Context context, String hostName) {
        return DaggerPayOutAPIComponent.builder()
                .payOutAPIModule(new PayOutAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

}
