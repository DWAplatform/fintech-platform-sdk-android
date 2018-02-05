package com.fintechplatform.android.sample.auth.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.sample.auth.api.AuthenticationAPIModule;
import com.fintechplatform.android.sample.auth.keys.KeyChainModule;

/**
 * Created by ingrid on 19/12/17.
 */

public class AuthUI {

    protected static AuthUI instance;
    private String userid;
    private String hostname;
    private String tenantid;

    public AuthUI(){ }

    AuthUI(String hostname, String userid, String tenantid){
        this.hostname = hostname;
        this.userid = userid;
        this.tenantid = tenantid;
    }

    protected AuthViewComponent buildAuthViewComponent(AuthenticationContract.View view, Context context){
        return DaggerAuthViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostname))
                .authenticationPresenterModule(new AuthenticationPresenterModule(view, userid, tenantid))
                .authenticationAPIModule(new AuthenticationAPIModule(hostname))
                .keyChainModule(new KeyChainModule(context))
                .build();
    }

    public static AuthViewComponent createAuthViewComponent(AuthenticationContract.View view, Context context){
        return instance.buildAuthViewComponent(view, context);
    }

    public void start(Context context){
        instance = this;
        Intent intent = new Intent(context, AuthenticationActivity.class);
        context.startActivity(intent);
    }
}
