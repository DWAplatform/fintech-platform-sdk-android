package com.fintechplatform.android.enterprise;


import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.enterprise.api.EnterpriseAPIModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.profile.api.ProfileAPIModule;

public class EnterpriseBuilder {

    public EnterpriseAPIComponent createEnterpriseAPIComponent(String hostName, Context context) {
        return DaggerEnterpriseAPIComponent.builder()
                .enterpriseAPIModule(new EnterpriseAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }
}
