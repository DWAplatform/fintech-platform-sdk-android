package com.fintechplatform.ui.profile.lightdata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.lightdata.di.DaggerLightDataViewComponent
import com.fintechplatform.ui.profile.lightdata.di.LightDataPresenterModule
import com.fintechplatform.ui.profile.lightdata.di.LightDataViewComponent

class LightDataUI(private val hostName: String, private val dataAccount: DataAccount) {

    fun createFragment(): LightDataFragment {
        return LightDataFragment.newInstance(hostName, dataAccount)
    }

    fun startActivity(context: Context) {
        val intent = Intent(context, LightDataActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", dataAccount)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    object Builder {
        fun buildLightDataViewComponent(context: Context, view: LightDataContract.View, hostName: String, dataAccount: DataAccount): LightDataViewComponent {
            return DaggerLightDataViewComponent.builder()
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .profileAPIModule(ProfileAPIModule(hostName))
                    .lightDataPresenterModule(LightDataPresenterModule(view, dataAccount))
                    .idsDocumentsAPIModule(IdsDocumentsAPIModule(hostName))
                    .build()
        }
    }
}