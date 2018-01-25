package com.dwaplatform.android.profile.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.api.NetHelper;
import com.dwaplatform.android.log.Log;

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
