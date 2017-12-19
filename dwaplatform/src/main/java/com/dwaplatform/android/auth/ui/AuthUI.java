package com.dwaplatform.android.auth.ui;

import android.content.Context;
import android.content.Intent;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.api.AuthenticationAPIModule;

/**
 * Created by ingrid on 19/12/17.
 */

public class AuthUI {

    protected static AuthUI instance;
    private String userid;
    private String hostname;

    public AuthUI(){ }

    AuthUI(String hostname, String userid){
        this.hostname = hostname;
        this.userid = userid;
    }

    protected AuthViewComponent buildAuthViewComponent(AuthenticationContract.View view, Context context){
        return DaggerAuthViewComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .authenticationPresenterModule(new AuthenticationPresenterModule(view, userid))
                .authenticationAPIModule(new AuthenticationAPIModule(hostname))
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
