package com.fintechplatform.android.api;

import com.android.volley.RequestQueue;
import com.fintechplatform.android.api.volley.VolleyRequestProvider;
import com.fintechplatform.android.api.volley.VolleyRequestQueueProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetModule {

    private RequestQueue requestQueue;
    private String hostName;

    public NetModule(RequestQueue requestQueue, String hostName) {
        this.requestQueue = requestQueue;
        this.hostName = hostName;
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
    NetHelper providesNetHelper() {
        return new NetHelper(hostName);
    }
}