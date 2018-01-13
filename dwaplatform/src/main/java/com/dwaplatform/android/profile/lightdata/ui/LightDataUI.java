package com.dwaplatform.android.profile.lightdata.ui;


import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.api.ProfileAPIModule;

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
                .netModule(new NetModule(Volley.newRequestQueue(context)))
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
