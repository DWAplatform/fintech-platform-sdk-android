package com.dwaplatform.android.account.balance.api;

import com.android.volley.RequestQueue;
import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.api.volley.VolleyRequestProvider;
import com.dwaplatform.android.api.volley.VolleyRequestQueueProvider;
import com.dwaplatform.android.card.helpers.JSONHelper;
import com.dwaplatform.android.log.Log;
import com.dwaplatform.android.payin.api.PayInAPI;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class BalanceAPIModule {

    private RequestQueue requestQueue;
    private String hostName;
    private String token;


    public BalanceAPIModule(String hostName, String token, RequestQueue requestQueue) {
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
    BalanceAPI provideBalanceAPI(IRequestQueue queue, IRequestProvider requestProvider, JSONHelper jsonHelper, Log log) {
        return new BalanceAPI(hostName, token, queue, requestProvider, jsonHelper, log);
    }
}

