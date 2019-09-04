package com.fintechplatform.ui.cashin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.card.api.PaymentCardAPIModule
import com.fintechplatform.api.cashin.api.CashInAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.models.DataAccount


class CashInUI(private val hostName: String,
               private val configuration: DataAccount,
               private val sandbox: Boolean) {

    fun startActivity(context: Context, amount: Long?) {
        val intent = Intent(context, CashInActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        args.putBoolean("isSandbox", sandbox)
        amount?.let { args.putLong("initialAmount", amount) }
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment(amount: Long?): com.fintechplatform.ui.cashin.ui.CashInFragment {
        return com.fintechplatform.ui.cashin.ui.CashInFragment.Companion.newInstance(hostName, configuration, sandbox, amount)
    }

    object Builder {
        /**
         * Resolve with default dependencies for cash in view component
         */

        fun buildPayInViewComponent(context: Context, v: CashInContract.View, dataAccount: DataAccount, hostName: String, isSandbox: Boolean?): CashInViewComponent {
            return DaggerCashInViewComponent.builder()
                    .cashInPresenterModule(CashInPresenterModule(v, dataAccount))
                    .cashInAPIModule(CashInAPIModule(hostName))
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .balanceAPIModule(BalanceAPIModule(hostName))
                    .paymentCardAPIModule(PaymentCardAPIModule(hostName, isSandbox!!))
                    .build()
        }
    }
}
