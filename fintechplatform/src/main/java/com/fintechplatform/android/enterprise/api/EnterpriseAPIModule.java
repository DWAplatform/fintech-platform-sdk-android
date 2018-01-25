package com.fintechplatform.android.enterprise.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.api.NetHelper;
import com.fintechplatform.android.log.Log;

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
    EnterpriseAPI providesEnterpriseAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper){
        return new EnterpriseAPI(hostName, queue, requestProvider, log, netHelper);
    }
}
