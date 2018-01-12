package com.dwaplatform.android.profile.jobinfo.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.api.ProfileAPIModule;

/**
 * Created by ingrid on 09/01/18.
 */

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
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .keyChainModule(new KeyChainModule(context))
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
