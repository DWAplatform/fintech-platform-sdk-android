package com.fintechplatform.ui.account.balance

import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetModule::class, 
    BalanceAPIModule::class, 
    BalanceHelperModule::class, 
    LogModule::class
])
interface BalanceHelperComponent {
    val balanceHelper: BalanceHelper
}
