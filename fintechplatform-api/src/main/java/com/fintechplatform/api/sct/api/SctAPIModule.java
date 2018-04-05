package com.fintechplatform.api.sct.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.IRequestProvider;
import com.fintechplatform.api.net.IRequestQueue;
import com.fintechplatform.api.net.NetHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 22/02/18.
 */
@Module
public class SctAPIModule {
    @Provides
    @Singleton
    SctAPI provideBalanceAPI(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper) {
        return new SctAPI(queue, requestProvider, log, netHelper);
    }
}
