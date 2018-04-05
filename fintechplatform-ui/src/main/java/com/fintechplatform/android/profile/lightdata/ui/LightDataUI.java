package com.fintechplatform.android.profile.lightdata.ui;


import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.profile.api.ProfileAPIModule;

public class LightDataUI {

    private String hostName;
    private DataAccount configuration;

    static LightDataUI instance;

    public LightDataUI(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    LightDataViewComponent buildLightDataViewComponent(Context context, LightDataContract.View view) {
        return DaggerLightDataViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), instance.hostName))
                .profileAPIModule(new ProfileAPIModule(instance.hostName))
                .lightDataPresenterModule(new LightDataPresenterModule(view, instance.configuration))
                .build();
    }
    public LightDataViewComponent createLightDataViewComponent(Context context, LightDataContract.View view) {
        return instance.buildLightDataViewComponent(context, view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, LightDataActivity.class);
        context.startActivity(intent);
    }
}
