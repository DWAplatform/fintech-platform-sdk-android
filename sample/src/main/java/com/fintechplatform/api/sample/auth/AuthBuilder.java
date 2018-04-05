package com.fintechplatform.api.sample.auth;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.net.NetModule;
import com.fintechplatform.api.sample.auth.api.AuthenticationAPIModule;
import com.fintechplatform.api.sample.auth.keys.DaggerKeyChainComponent;
import com.fintechplatform.api.sample.auth.keys.KeyChainComponent;
import com.fintechplatform.api.sample.auth.keys.KeyChainModule;
import com.fintechplatform.api.sample.auth.ui.AuthUIModule;


public class AuthBuilder {
    public AuthAPIComponent createAuthAPI(String hostName, Context context) {
        return DaggerAuthAPIComponent.builder()
                .authenticationAPIModule(new AuthenticationAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

    public KeyChainComponent createKeyChain(Context context){
        return DaggerKeyChainComponent.builder()
                .keyChainModule(new KeyChainModule(context))
                .build();
    }

    public AuthUIComponent createAuthUI(String userid, String hostname, String tenantid) {
        return DaggerAuthUIComponent.builder()
                .authUIModule(new AuthUIModule(userid, hostname, tenantid))
                .build();
    }
}
