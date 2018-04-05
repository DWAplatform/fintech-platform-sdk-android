package com.fintechplatform.api.sample.contactslist.api;

import com.fintechplatform.api.log.Log;
import com.fintechplatform.api.net.net.IRequestProvider;
import com.fintechplatform.api.net.net.IRequestQueue;
import com.fintechplatform.api.net.net.NetHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 09/02/18.
 */
@Module
public class PeersApiModule {
    @Provides
    @Singleton
    PeersAPI providesAuthApi(IRequestQueue queue, IRequestProvider requestProvider, Log log, NetHelper netHelper){
        return new PeersAPI(queue, requestProvider, log, netHelper);
    }
}
