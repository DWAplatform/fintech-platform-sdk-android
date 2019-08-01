package com.fintechplatform.api.cashout;


import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.cashout.api.CashOutAPIModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;

public class CashOutAPIBuilder {

    public CashOutAPIComponent createCashOutAPI(Context context, String hostName) {
        return DaggerCashOutAPIComponent.builder()
                .cashOutAPIModule(new CashOutAPIModule(hostName))
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), hostName)))
                .build();
    }

}
