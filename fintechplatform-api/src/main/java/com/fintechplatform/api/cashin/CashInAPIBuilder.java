package com.fintechplatform.api.cashin;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.cashin.api.CashInAPIModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;

public class CashInAPIBuilder {

    public CashInAPIComponent createCashInAPIComponent(String hostName, Context context) {
        return DaggerCashInAPIComponent.builder()
                .cashInAPIModule(new CashInAPIModule(hostName))
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), hostName)))
                .build();
    }

}