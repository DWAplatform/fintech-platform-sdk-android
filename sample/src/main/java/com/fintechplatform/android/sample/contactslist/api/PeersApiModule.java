package com.fintechplatform.android.sample.contactslist.api;

import com.fintechplatform.android.api.net.IRequestProvider;
import com.fintechplatform.android.api.net.IRequestQueue;
import com.fintechplatform.android.api.net.NetHelper;
import com.fintechplatform.android.log.Log;

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
