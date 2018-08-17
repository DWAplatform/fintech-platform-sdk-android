package com.fintechplatform.api.account

import com.fintechplatform.api.account.api.AccountAPI
import com.fintechplatform.api.account.api.AccountAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AccountAPIModule::class,
    NetModule::class,
    LogModule::class
])
interface AccountAPIComponent {
    fun getAccountAPI(): AccountAPI
}