package com.fintechplatform.ui.transfer.di

import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.transfer.api.TransferAPIModule
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.images.ImageHelperModule
import com.fintechplatform.ui.money.FeeHelperModule
import com.fintechplatform.ui.money.MoneyHelperModule
import com.fintechplatform.ui.transfer.TransferFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TransferPresenterModule::class, 
    TransferAPIModule::class, 
    BalanceAPIModule::class, 
    AlertHelpersModule::class, 
    BalanceHelperModule::class, 
    MoneyHelperModule::class, 
    FeeHelperModule::class, 
    ImageHelperModule::class, 
    LogModule::class, 
    NetModule::class
])
interface TransferViewComponent {
    fun inject(fragment: TransferFragment)
}