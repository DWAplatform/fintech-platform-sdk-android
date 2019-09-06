package com.fintechplatform.ui.payin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.card.api.PaymentCardAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.payin.api.PayInAPIModule
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.payin.di.PayInFragment


class PayInUI(private val hostName: String,
              private val configuration: DataAccount,
              private val sandbox: Boolean) {

    fun startActivity(context: Context, amount: Long?=null) {
        val intent = Intent(context, PayInActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        args.putBoolean("isSandbox", sandbox)
        amount?.let { args.putLong("initialAmount", amount) }
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment(amount: Long?): PayInFragment {
        return PayInFragment.newInstance(hostName, configuration, sandbox, amount)
    }

    object Builder {
        /**
         * Resolve with default dependencies for pay in view component
         */

        fun buildPayInViewComponent(context: Context, v: PayInContract.View, dataAccount: DataAccount, hostName: String, isSandbox: Boolean?): PayInViewComponent {
            return DaggerPayInViewComponent.builder()
                    .payInPresenterModule(PayInPresenterModule(v, dataAccount))
                    .payInAPIModule(PayInAPIModule(hostName))
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .balanceAPIModule(BalanceAPIModule(hostName))
                    .paymentCardAPIModule(PaymentCardAPIModule(hostName, isSandbox!!))
                    .build()
        }
    }
}
