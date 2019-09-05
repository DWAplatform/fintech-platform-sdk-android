package com.fintechplatform.ui.payin.di

import android.content.Context
import com.fintechplatform.ui.card.di.PaymentCardUIFactory
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.payin.PayInContract
import com.fintechplatform.ui.payin.PayInViewComponent
import com.fintechplatform.ui.secure3d.ui.Secure3DUIFactory

interface PayInUIFactory: PaymentCardUIFactory, Secure3DUIFactory {
    fun createPayInViewComponent(context: Context, v: PayInContract.View, dataAccount: DataAccount, hostName: String, isSandbox: Boolean): PayInViewComponent
}