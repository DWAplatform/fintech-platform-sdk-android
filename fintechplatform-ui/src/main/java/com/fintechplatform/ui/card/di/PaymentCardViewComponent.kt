package com.fintechplatform.ui.card.di

import com.fintechplatform.api.card.api.PaymentCardAPIModule
import com.fintechplatform.api.card.helpers.PaymentCardHelperModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.card.PaymentCardFragment
import com.fintechplatform.ui.card.db.PaymentCardPersistanceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    PaymentCardPresenterModule::class, 
    AlertHelpersModule::class, 
    PaymentCardAPIModule::class, 
    LogModule::class, 
    PaymentCardHelperModule::class, 
    NetModule::class, 
    PaymentCardPersistanceModule::class
])
interface PaymentCardViewComponent {
    fun inject(fragment: PaymentCardFragment)
}
