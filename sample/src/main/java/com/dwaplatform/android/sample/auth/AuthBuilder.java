package com.dwaplatform.android.sample.auth;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.sample.auth.api.AuthenticationAPIModule;
import com.dwaplatform.android.sample.auth.keys.DaggerKeyChainComponent;
import com.dwaplatform.android.sample.auth.keys.KeyChainComponent;
import com.dwaplatform.android.sample.auth.keys.KeyChainModule;
import com.dwaplatform.android.sample.auth.ui.AuthUIModule;


public class AuthBuilder {
    public AuthAPIComponent createAuthAPI(String hostName, Context context) {
        return DaggerAuthAPIComponent.builder()
                .authenticationAPIModule(new AuthenticationAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context)))
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
