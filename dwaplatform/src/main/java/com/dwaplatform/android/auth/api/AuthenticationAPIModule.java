package com.dwaplatform.android.auth.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 19/12/17.
 */
@Module
public class AuthenticationAPIModule {

    private String hostname;
    private String token;

    public AuthenticationAPIModule(String hostname, String token){
        this.hostname = hostname;
        this.token = token;
    }

    @Provides
    @Singleton
    AuthenticationAPI providesAuthApi(IRequestQueue queue, IRequestProvider requestProvider, Log log){
        return new AuthenticationAPI(hostname, token, queue, requestProvider, log);
    }
}
