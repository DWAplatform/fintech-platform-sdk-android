package com.fintechplatform.ui.cashin.di

import android.content.Context
import com.fintechplatform.ui.card.ui.PaymentCardUIFactory
import com.fintechplatform.ui.cashin.CashInContract
import com.fintechplatform.ui.cashin.CashInViewComponent
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.secure3d.ui.Secure3DUIFactory

interface CashInUIFactory: PaymentCardUIFactory, Secure3DUIFactory {
    fun createPayInViewComponent(context: Context, v: CashInContract.View, dataAccount: DataAccount, hostName: String, isSandbox: Boolean): CashInViewComponent
}