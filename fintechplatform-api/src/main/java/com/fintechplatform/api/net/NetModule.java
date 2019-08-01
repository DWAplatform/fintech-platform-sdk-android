package com.fintechplatform.api.net;

import com.android.volley.RequestQueue;
import com.fintechplatform.api.net.volley.VolleyRequestProvider;
import com.fintechplatform.api.net.volley.VolleyRequestQueueProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetModule {

    private RequestQueue requestQueue;
    private String hostName;

    public NetModule(NetData data) {
        this.requestQueue = data.getRequestQueue();
        this.hostName = data.getHostName();
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