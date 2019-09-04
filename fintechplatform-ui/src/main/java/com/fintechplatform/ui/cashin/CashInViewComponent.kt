package com.fintechplatform.ui.cashin

import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.card.api.PaymentCardAPIModule
import com.fintechplatform.api.cashin.api.CashInAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.account.balance.helpers.BalanceHelperModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.card.db.PaymentCardPersistanceModule
import com.fintechplatform.ui.cashin.ui.CashInFragment
import com.fintechplatform.ui.money.FeeHelperModule
import com.fintechplatform.ui.money.MoneyHelperModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    CashInPresenterModule::class,
    AlertHelpersModule::class,
    LogModule::class,
    CashInAPIModule::class,
    MoneyHelperModule::class,
    BalanceAPIModule::class,
    BalanceHelperModule::class,
    PaymentCardPersistanceModule::class,
    PaymentCardAPIModule::class,
    FeeHelperModule::class,
    NetModule::class
])
interface CashInViewComponent {
    fun inject(fragment: CashInFragment)
}