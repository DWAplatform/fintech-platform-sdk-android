package com.fintechplatform.api.pagopa;

import android.content.Context;

import com.android.volley.toolbox.Volley;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.pagopa.api.PagoPaAPIModule;

/**
 * Created by ingrid on 18/04/18.
 */

public class PagoPaAPIBuilder {
    public PagoPaAPIComponent createPayInAPIComponent(String hostName, Context context) {
        return DaggerPagoPaAPIComponent.builder()
                .pagoPaAPIModule(new PagoPaAPIModule(hostName))
                .netModule(new NetModule(Volley.newRequestQueue(context), hostName))
                .build();
    }
}
