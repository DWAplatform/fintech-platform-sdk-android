package com.fintechplatform.android.sample.auth;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.sample.auth.api.AuthenticationAPIModule;
import com.fintechplatform.android.sample.auth.keys.DaggerKeyChainComponent;
import com.fintechplatform.android.sample.auth.keys.KeyChainComponent;
import com.fintechplatform.android.sample.auth.keys.KeyChainModule;
import com.fintechplatform.android.sample.auth.ui.AuthUIModule;


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

    public AuthUIComponent createAuthUI(String userid, String hostname) {
        return DaggerAuthUIComponent.builder()
                .authUIModule(new AuthUIModule(userid, hostname))
                .build();
    }
}
