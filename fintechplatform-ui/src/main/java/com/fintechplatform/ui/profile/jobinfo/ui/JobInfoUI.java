package com.fintechplatform.ui.profile.jobinfo.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;
import com.fintechplatform.ui.models.DataAccount;

public class JobInfoUI {

    private String hostName;
    private DataAccount configuration;

    protected static JobInfoUI instance;

    public JobInfoUI(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    protected JobInfoViewComponent buildJobInfoComponent(Context context, JobInfoContract.View view){
        return DaggerJobInfoViewComponent.builder()
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), instance.hostName)))
                .jobInfoPresenterModule(new JobInfoPresenterModule(view, instance.configuration))
                .profileAPIModule(new ProfileAPIModule(instance.hostName))
                .build();
    }
    public JobInfoViewComponent createJobInfoComponent(Context context, JobInfoContract.View view){
        return instance.buildJobInfoComponent(context, view);
    }

    public void start(Context context) {
        instance = this;
        Intent intent = new Intent(context, JobInfoActivity.class);
        context.startActivity(intent);
    }
}
