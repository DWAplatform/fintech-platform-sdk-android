package com.fintechplatform.api.profile.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileAPIModule {

    private String hostName;

    public ProfileAPIModule(String hostName) {
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    ProfileAPI providesProfileAPI(IdsDocumentsAPI restAPI, IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new ProfileAPI(restAPI, hostName, queue, requestProvider, log, netHelper);
    }
}
