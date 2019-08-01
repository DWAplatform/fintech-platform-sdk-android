package com.fintechplatform.ui.di.modules

import android.app.Application
import com.fintechplatform.api.FintechPlatformAPI
import com.fintechplatform.api.account.balance.api.BalanceAPI
import com.fintechplatform.api.card.api.PaymentCardAPI
import com.fintechplatform.api.cashin.api.CashInAPI
import dagger.Module
import javax.inject.Inject

@Module
class ApiHelpersModule @Inject constructor(val app: Application) {
    private val hostname = "http://api.sandbox.lightbankingservices.com"
    private val isSandbox = true

    fun topUpAPI() : CashInAPI {
        return FintechPlatformAPI.getPayIn(hostname, app)
    }

    fun paymentCardAPI() : PaymentCardAPI {
        return FintechPlatformAPI.getPaymentCard(hostname, app, isSandbox)
    }

    fun balanceAPI(): BalanceAPI {
        return FintechPlatformAPI.getBalance(hostname, app)
    }

}