package com.fintechplatform.api.sct;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetData;
import com.fintechplatform.api.net.NetModule;

/**
 * Created by ingrid on 22/02/18.
 */

public class SctAPIBuilder {

    public SctAPIComponent createSctAPIComponent(Context context, String hostName) {
        return DaggerSctAPIComponent.builder()
                .netModule(new NetModule(new NetData(Volley.newRequestQueue(context), hostName)))
                .build();
    }

}
