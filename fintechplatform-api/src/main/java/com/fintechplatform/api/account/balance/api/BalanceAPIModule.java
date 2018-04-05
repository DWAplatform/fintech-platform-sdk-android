package com.fintechplatform.api.account.balance.api;

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
public class BalanceAPIModule {

    private String hostName;

    public BalanceAPIModule(String hostName) {
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    BalanceAPI provideBalanceAPI(IRequestQueue queue, IRequestProvider requestProvider, NetHelper netHelper, Log log) {
        return new BalanceAPI(hostName, queue, requestProvider, netHelper, log);
    }
}