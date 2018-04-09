package com.fintechplatform.api.cashout;


import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.cashout.api.CashOutAPIModule;

public class CashOutAPIBuilder {

    public CashOutAPIComponent createCashOutAPI(Context context, String hostName) {
        return DaggerCashOutAPIComponent.builder()
                .cashOutAPIModule(new CashOutAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

}
