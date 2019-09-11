package com.fintechplatform.api.profile;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;

public class ProfileAPIBuilder {

    public ProfileAPIComponent createProfileAPIComponent(String hostName, Context context) {
        return DaggerProfileAPIComponent.builder()
                .idsDocumentsAPIModule(new IdsDocumentsAPIModule((hostName)))
                .profileAPIModule(new ProfileAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }
}
