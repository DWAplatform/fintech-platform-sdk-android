package com.fintechplatform.api.sample.auth.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.net.IRequestProvider;
import com.fintechplatform.api.net.net.IRequestQueue;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 19/12/17.
 */
@Module
public class AuthenticationAPIModule {

    private String hostname;

    public AuthenticationAPIModule(String hostname){
        this.hostname = hostname;
    }

    @Provides
    @Singleton
    AuthenticationAPI providesAuthApi(IRequestQueue queue, IRequestProvider requestProvider, Log log){
        return new AuthenticationAPI(hostname, queue, requestProvider, log);
    }
}
