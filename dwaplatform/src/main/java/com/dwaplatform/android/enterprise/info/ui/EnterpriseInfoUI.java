package com.dwaplatform.android.enterprise.info.ui;


import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.enterprise.api.EnterpriseAPIModule;
import com.dwaplatform.android.models.DataAccount;

public class EnterpriseInfoUI {

    private String hostName;
    private DataAccount configuration;

    static EnterpriseInfoUI instance;

    public EnterpriseInfoUI(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    EnterpriseInfoViewComponent buildLightDataViewComponent(Context context, EnterpriseInfoContract.View view) {
        return DaggerEnterpriseInfoViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .enterpriseAPIModule(new EnterpriseAPIModule(instance.hostName))
                .enterpriseInfoPresenterModule(new EnterpriseInfoPresenterModule(view, instance.configuration))
                .build();
    }
    public EnterpriseInfoViewComponent createLightDataViewComponent(Context context, EnterpriseInfoContract.View view) {
        return instance.buildLightDataViewComponent(context, view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, EnterpriseInfoActivity.class);
        context.startActivity(intent);
    }
}
