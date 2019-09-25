package com.fintechplatform.api.profile.api

import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProfileAPIModule(private val hostName: String) {

    @Provides
    @Singleton
    internal fun providesProfileAPI(restAPI: IdsDocumentsAPI, queue: IRequestQueue, requestProvider: IRequestProvider, log: Log, netHelper: NetHelper): ProfileAPI {
        return ProfileAPI(restAPI, hostName, queue, requestProvider, log, netHelper)
    }
}
