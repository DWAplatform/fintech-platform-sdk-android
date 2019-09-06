package com.fintechplatform.ui.payout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.cashout.api.CashOutAPIModule
import com.fintechplatform.api.iban.api.IbanAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.payout.di.DaggerPayOutViewComponent
import com.fintechplatform.ui.payout.di.PayOutPresenterModule
import com.fintechplatform.ui.payout.di.PayOutViewComponent


class PayOutUI( val hostName: String, 
                val configuration: DataAccount) {

    fun startActivity(context: Context) {
        val intent = Intent(context, PayOutActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment() : PayOutFragment {
        return PayOutFragment.newInstance(hostName, configuration)
    }

    object Builder {
        /**
         * Resolve with default decendencies for pay out in view component
         */

        fun buildPayOutViewComponent(context: Context, view: PayOutContract.View, hostName: String, dataAccount: DataAccount): PayOutViewComponent {
            return DaggerPayOutViewComponent.builder()
                    .payOutPresenterModule(PayOutPresenterModule(view, dataAccount))
                    .cashOutAPIModule(CashOutAPIModule(hostName))
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .balanceAPIModule(BalanceAPIModule(hostName))
                    .ibanAPIModule(IbanAPIModule(hostName))
                    .build()
        }
    }

}