package com.fintechplatform.android.sct.api;

import com.fintechplatform.android.api.IRequestProvider;
import com.fintechplatform.android.api.IRequestQueue;
import com.fintechplatform.android.api.NetHelper;
import com.fintechplatform.android.log.Log;

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
