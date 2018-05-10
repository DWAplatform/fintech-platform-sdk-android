package com.fintechplatform.api.user;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.user.api.ProfileAPIModule;

public class ProfileAPIBuilder {

    public ProfileAPIComponent createProfileAPIComponent(String hostName, Context context) {
        return DaggerProfileAPIComponent.builder()
                .profileAPIModule(new ProfileAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }
}
