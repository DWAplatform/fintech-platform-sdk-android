package com.dwaplatform.android.auth;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.api.AuthenticationAPIModule;
import com.dwaplatform.android.auth.ui.AuthUIModule;
import com.dwaplatform.android.keys.DaggerKeyChainComponent;
import com.dwaplatform.android.keys.KeyChainComponent;
import com.dwaplatform.android.keys.KeyChainModule;

/**
 * Created by ingrid on 19/12/17.
 */

public class AuthBuilder {
    public AuthAPIComponent createAuthAPI(String hostName, Context context) {
        return DaggerAuthAPIComponent.builder()
                .authenticationAPIModule(new AuthenticationAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .build();
    }
    //todo sharedpreferences need application context?

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
