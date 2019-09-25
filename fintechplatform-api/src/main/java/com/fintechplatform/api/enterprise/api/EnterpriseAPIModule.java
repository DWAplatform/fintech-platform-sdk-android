package com.fintechplatform.api.enterprise.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;
import com.fintechplatform.api.profile.api.IdsDocumentsAPI;

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
    EnterpriseAPI providesEnterpriseAPI(IdsDocumentsAPI restAPI, IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper){
        return new EnterpriseAPI(restAPI, hostName, queue, requestProvider, log, netHelper);
    }
}
