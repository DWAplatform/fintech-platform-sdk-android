package com.fintechplatform.api.account.kyc.di

import com.fintechplatform.api.account.kyc.KycAPI
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, KycAPIModule::class, LogModule::class])
interface KycAPIComponent {
    val kycAPI: KycAPI
}