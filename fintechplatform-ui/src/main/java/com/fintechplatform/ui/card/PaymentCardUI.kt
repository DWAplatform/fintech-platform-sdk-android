package com.fintechplatform.ui.card

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.card.api.PaymentCardAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.card.di.DaggerPaymentCardViewComponent
import com.fintechplatform.ui.card.di.PaymentCardPresenterModule
import com.fintechplatform.ui.card.di.PaymentCardViewComponent
import com.fintechplatform.ui.models.DataAccount


class PaymentCardUI(private val hostname: String, private val dataAccount: DataAccount, private val sandbox: Boolean) {

    fun start(context: Context) {
        val intent = Intent(context, PaymentCardActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostname)
        args.putParcelable("dataAccount", dataAccount)
        args.putBoolean("isSandbox", sandbox)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment(hostname: String, dataAccount: DataAccount, sandbox: Boolean): PaymentCardFragment {
        return PaymentCardFragment.newInstance(hostname, dataAccount, sandbox)
    }

    object Builder {
        fun buildPaymentCardComponent(context: Context, view: PaymentCardContract.View, hostname: String, dataAccount: DataAccount, sandbox: Boolean): PaymentCardViewComponent {
            return DaggerPaymentCardViewComponent.builder()
                    .paymentCardPresenterModule(PaymentCardPresenterModule(view, dataAccount))
                    .paymentCardAPIModule(PaymentCardAPIModule(hostname, sandbox))
                    .netModule(NetModule(Volley.newRequestQueue(context), hostname))
                    .build()
        }
    }

}