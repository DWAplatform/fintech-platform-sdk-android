package com.fintechplatform.android.sct;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.sct.ui.SctUIModule;

/**
 * Created by ingrid on 22/02/18.
 */

public class SctBuilder {
    public SctUIComponent createSctUIComponent(String hostName, DataAccount configuration) {
        return DaggerSctUIComponent.builder()
                .sctUIModule(new SctUIModule(configuration, hostName))
                .build();
    }

    public SctAPIComponent createSctAPIComponent(Context context, String hostName) {
        return DaggerSctAPIComponent.builder()
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }

}
