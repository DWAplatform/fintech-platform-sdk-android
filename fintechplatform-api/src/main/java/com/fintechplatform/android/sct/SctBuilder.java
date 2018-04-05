package com.fintechplatform.android.sct;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.net.NetModule;

/**
 * Created by ingrid on 22/02/18.
 */

public class SctBuilder {

    public SctAPIComponent createSctAPIComponent(Context context, String hostName) {
        return DaggerSctAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

}
