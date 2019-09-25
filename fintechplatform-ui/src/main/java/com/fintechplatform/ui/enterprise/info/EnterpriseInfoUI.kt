package com.fintechplatform.ui.enterprise.info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.ui.enterprise.info.di.DaggerEnterpriseInfoViewComponent
import com.fintechplatform.ui.enterprise.info.di.EnterpriseInfoPresenterModule
import com.fintechplatform.ui.enterprise.info.di.EnterpriseInfoViewComponent
import com.fintechplatform.ui.models.DataAccount


class EnterpriseInfoUI(private val hostName: String, private val configuration: DataAccount) {

    fun startActivity(context: Context) {
        val intent = Intent(context, EnterpriseInfoActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment(): EnterpriseInfoFragment {
        return EnterpriseInfoFragment.newInstance(hostName, configuration)
    }

     object Builder {
         fun buildLightDataViewComponent(context: Context, view: EnterpriseInfoContract.View, hostName: String, dataAccount: DataAccount): EnterpriseInfoViewComponent {
             return DaggerEnterpriseInfoViewComponent.builder()
                     .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                     .enterpriseAPIModule(EnterpriseAPIModule(hostName))
                     .idsDocumentsAPIModule(IdsDocumentsAPIModule(hostName))
                     .enterpriseInfoPresenterModule(EnterpriseInfoPresenterModule(view, dataAccount))
                     .build()
         }
     }
}