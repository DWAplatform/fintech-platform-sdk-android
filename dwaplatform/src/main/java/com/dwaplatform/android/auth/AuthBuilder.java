package com.dwaplatform.android.auth;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.api.AuthenticationAPI;
import com.dwaplatform.android.auth.api.AuthenticationAPIModule;

/**
 * Created by ingrid on 19/12/17.
 */

public class AuthBuilder {
    public AuthAPIComponent createAuthAPI(String hostName, String token, Context context) {
        return DaggerAuthAPIComponent.builder()
                .authenticationAPIModule(new AuthenticationAPIModule(hostName, token))
                .netModule(new NetModule(Volley.newRequestQueue(context)))
                .build();
    }
}
