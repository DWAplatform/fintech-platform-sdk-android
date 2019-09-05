package com.fintechplatform.ui.account.balance

import android.content.Context
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.account.balance.api.BalanceAPIModule
import com.fintechplatform.api.net.NetModule

class BalanceBuilder {
    fun createBalanceHelperComponent(hostName: String, context: Context): BalanceHelperComponent = 
            DaggerBalanceHelperComponent.builder()
                .balanceAPIModule(BalanceAPIModule(hostName))
                .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                .build()
}