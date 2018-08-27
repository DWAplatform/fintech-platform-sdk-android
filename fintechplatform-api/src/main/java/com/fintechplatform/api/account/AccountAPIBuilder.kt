package com.fintechplatform.api.account

import android.content.Context
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.account.api.AccountAPIModule
import com.fintechplatform.api.net.NetModule

class AccountAPIBuilder {
    fun createAccountAPIComponent(context: Context, hostName: String): AccountAPIComponent {
        return DaggerAccountAPIComponent.builder()
                .accountAPIModule(AccountAPIModule(hostName))
                .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                .build()
    }
}