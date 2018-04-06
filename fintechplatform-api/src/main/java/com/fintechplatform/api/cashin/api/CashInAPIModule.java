package com.fintechplatform.api.cashin.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class CashInAPIModule {

    private String hostName;


    public CashInAPIModule(String hostName) {
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    CashInAPI providePayInAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new CashInAPI(hostName, queue, requestProvider, log, netHelper);
    }
}
