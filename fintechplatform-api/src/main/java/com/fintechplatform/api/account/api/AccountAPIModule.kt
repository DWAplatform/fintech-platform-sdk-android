package com.fintechplatform.api.account.api

import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AccountAPIModule(private val hostName: String) {
    @Provides
    @Singleton
    fun provideAccountAPI(queue: IRequestQueue, requestProvider: IRequestProvider, log: Log, netHelper: NetHelper): AccountAPI {
        return AccountAPI(hostName, queue, requestProvider, log, netHelper)
    }
}