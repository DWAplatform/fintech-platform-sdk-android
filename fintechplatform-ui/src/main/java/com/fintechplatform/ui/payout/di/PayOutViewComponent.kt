package com.fintechplatform.ui.payout.di

import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.iban.api.IbanAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.payout.api.PayOutAPIModule
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.iban.db.IbanPersistanceDBModule
import com.fintechplatform.ui.money.FeeHelperModule
import com.fintechplatform.ui.money.MoneyHelperModule
import com.fintechplatform.ui.payout.PayOutFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AlertHelpersModule::class,
    LogModule::class,
    NetModule::class,
    MoneyHelperModule::class,
    PayOutPresenterModule::class,
    BalanceHelperModule::class,
    BalanceAPIModule::class,
    IbanAPIModule::class,
    FeeHelperModule::class,
    PayOutAPIModule::class,
    IbanPersistanceDBModule::class])
interface PayOutViewComponent {
    fun inject(fragment: PayOutFragment)
}