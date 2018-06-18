package com.fintechplatform.api.account.kyc.di

import android.content.Context
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.account.kyc.KycAPI
import com.fintechplatform.api.net.NetModule


class KycAPIBuilder {
    fun createKycAPI(hostName: String, context: Context): KycAPI {
        return DaggerKycAPIComponent.builder()
                .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                .kycAPIModule(KycAPIModule(hostName))
                .build().kycAPI
    }
}