package com.dwaplatform.android.payin.api;

import com.android.volley.RequestQueue;
import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.api.volley.VolleyRequestProvider;
import com.dwaplatform.android.api.volley.VolleyRequestQueueProvider;
import com.dwaplatform.android.card.helpers.JSONHelper;
import com.dwaplatform.android.log.Log;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class PayInAPIModule {

    private RequestQueue requestQueue;
    private String hostName;
    private String token;


    public PayInAPIModule(String hostName, String token, RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Provides
    @Singleton
    IRequestProvider provideJsonArrayRequest() {
        return new VolleyRequestProvider();
    }

    @Provides
    @Singleton
    IRequestQueue provideVolleyRequestQueue() {
        return new VolleyRequestQueueProvider(requestQueue);
    }


    @Provides
    @Singleton
    PayInAPI providePayInAPI(IRequestQueue queue, IRequestProvider requestProvider, JSONHelper jsonHelper, Log log) {
        return new PayInAPI(hostName, token, queue, requestProvider, jsonHelper, log);
    }
}
