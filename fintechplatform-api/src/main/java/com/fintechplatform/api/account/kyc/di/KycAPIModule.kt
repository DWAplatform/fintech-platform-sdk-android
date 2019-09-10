package com.fintechplatform.api.account.kyc.di

import com.fintechplatform.api.account.kyc.KycAPI
import com.fintechplatform.api.log.Log
import com.fintechplatform.api.net.IRequestProvider
import com.fintechplatform.api.net.IRequestQueue
import com.fintechplatform.api.net.NetHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class KycAPIModule constructor(val hostName: String) {

    @Provides
    @Singleton
    fun provideKycAPIModule(queue: IRequestQueue, requestProvider: IRequestProvider, netHelper: NetHelper, log: Log): KycAPI {
        return KycAPI(hostName, queue, requestProvider, log, netHelper)
    }
}