package com.fintechplatform.android.profile;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.profile.api.ProfileAPIModule;

public class ProfileBuilder {

    public ProfileAPIComponent createProfileAPIComponent(String hostName, Context context) {
        return DaggerProfileAPIComponent.builder()
                .profileAPIModule(new ProfileAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }
}
