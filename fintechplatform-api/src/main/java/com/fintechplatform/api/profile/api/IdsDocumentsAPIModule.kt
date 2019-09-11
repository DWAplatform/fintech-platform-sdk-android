package com.fintechplatform.api.profile.api

import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class IdsDocumentsAPIModule(val hostName: String) {
    @Provides
    @Singleton
    fun providesIdsDoc(queue: IRequestQueue, requestProvider: IRequestProvider, log: Log, netHelper: NetHelper) : IdsDocumentsAPI =
            IdsDocumentsAPI(hostName, queue, requestProvider, log, netHelper)
}