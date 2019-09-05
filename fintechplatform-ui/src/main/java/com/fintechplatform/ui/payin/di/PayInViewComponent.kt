package com.fintechplatform.ui.payin

import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.card.api.PaymentCardAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.payin.api.PayInAPIModule
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.card.db.PaymentCardPersistanceModule
import com.fintechplatform.ui.money.FeeHelperModule
import com.fintechplatform.ui.money.MoneyHelperModule
import com.fintechplatform.ui.payin.di.PayInFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    PayInPresenterModule::class,
    AlertHelpersModule::class,
    LogModule::class,
    PayInAPIModule::class,
    MoneyHelperModule::class,
    BalanceAPIModule::class,
    BalanceHelperModule::class,
    PaymentCardPersistanceModule::class,
    PaymentCardAPIModule::class,
    FeeHelperModule::class,
    NetModule::class
])
interface PayInViewComponent {
    fun inject(fragment: PayInFragment)
}