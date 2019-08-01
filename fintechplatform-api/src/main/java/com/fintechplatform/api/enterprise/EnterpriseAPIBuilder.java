package com.fintechplatform.api.enterprise;


import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;

public class EnterpriseAPIBuilder {

    public EnterpriseAPIComponent createEnterpriseAPIComponent(String hostName, Context context) {
        return DaggerEnterpriseAPIComponent.builder()
                .enterpriseAPIModule(new EnterpriseAPIModule(hostName))
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), hostName)))
                .build();
    }
}
