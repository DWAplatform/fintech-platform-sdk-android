package com.fintechplatform.ui.card.ui

import android.content.Context
import com.fintechplatform.ui.models.DataAccount

interface PaymentCardUIFactory {
    fun createPaymentCardComponent(context: Context, view: PaymentCardContract.View, dataAccount: DataAccount, hostName: String, isSandbox: Boolean): PaymentCardViewComponent
}