package com.dwaplatform.android.api;

import com.android.volley.RequestQueue;
import com.dwaplatform.android.api.volley.VolleyRequestProvider;
import com.dwaplatform.android.api.volley.VolleyRequestQueueProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 08/12/17.
 */

@Module
public class NetModule {

    private RequestQueue requestQueue;

    public NetModule(RequestQueue requestQueue) {
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

}