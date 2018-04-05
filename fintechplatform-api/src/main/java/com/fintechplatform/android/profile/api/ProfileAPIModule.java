package com.fintechplatform.android.profile.api;

import com.fintechplatform.android.log.Log;
import com.fintechplatform.android.net.IRequestProvider;
import com.fintechplatform.android.net.IRequestQueue;
import com.fintechplatform.android.net.NetHelper;

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
    ProfileAPI providesProfileAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new ProfileAPI(hostName, queue, requestProvider, log, netHelper);
    }
}
