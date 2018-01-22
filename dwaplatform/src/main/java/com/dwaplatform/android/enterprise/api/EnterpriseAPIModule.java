package com.dwaplatform.android.enterprise.api;

import com.dwaplatform.android.api.IRequestProvider;
import com.dwaplatform.android.api.IRequestQueue;
import com.dwaplatform.android.api.NetHelper;
import com.dwaplatform.android.log.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseAPIModule {
    private String hostName;

    public EnterpriseAPIModule(String hostName) {
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    EnterpriseProfileAPI providesEnterpriseAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper){
        return new EnterpriseProfileAPI(hostName, queue, requestProvider, log, netHelper);
    }
}
