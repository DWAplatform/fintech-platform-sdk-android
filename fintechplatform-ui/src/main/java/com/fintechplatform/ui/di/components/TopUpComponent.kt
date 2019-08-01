package com.fintechplatform.ui.di.components

import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.card.db.PaymentCardPersistanceModule
import com.fintechplatform.ui.card.ui.PaymentCardUIModule
import com.fintechplatform.ui.cashin.TopUpActivity
import com.fintechplatform.ui.cashin.ui.CashInPresenterModule
import com.fintechplatform.ui.cashin.ui.Secure3DUIHelperModule
import com.fintechplatform.ui.secure3d.ui.Secure3DUIModule
import dagger.Subcomponent
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Subcomponent(
        modules = [
            Secure3DUIModule::class,
            PaymentCardUIModule::class,
            CashInPresenterModule::class,
            LogModule::class,
            PaymentCardPersistanceModule::class,
            NetModule::class,
            Secure3DUIHelperModule::class
        ]
)
interface TopUpComponent: AndroidInjector<TopUpActivity> {

    @Subcomponent.Factory
    interface TopUpFactory : AndroidInjector.Factory<TopUpActivity> {

    }
}
