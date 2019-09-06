package com.fintechplatform.ui.transfer

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.transfer.api.TransferAPIModule
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.transfer.di.DaggerTransferViewComponent
import com.fintechplatform.ui.transfer.di.TransferPresenterModule
import com.fintechplatform.ui.transfer.di.TransferViewComponent

class TransferUI(private val hostName: String, private val dataAccount: DataAccount) {


    fun startActivity(context: Context, transferTo : DataAccount) {
        val intent = Intent(context, TransferActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", dataAccount)
        args.putParcelable("transferTo", transferTo)
        intent.putExtras(args)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun startFragment(transferTo : DataAccount) : TransferFragment {
        return TransferFragment.newInstance(hostName, dataAccount, transferTo)
    }

    object Builder {
        fun buildTransferViewComponent(context: Context, view: TransferContract.View, hostName: String, dataAccount: DataAccount): TransferViewComponent {
            return DaggerTransferViewComponent.builder()
                    .balanceAPIModule(BalanceAPIModule(hostName))
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .transferPresenterModule(TransferPresenterModule(view, dataAccount))
                    .transferAPIModule(TransferAPIModule(hostName))
                    .build()
        }
    }
}
