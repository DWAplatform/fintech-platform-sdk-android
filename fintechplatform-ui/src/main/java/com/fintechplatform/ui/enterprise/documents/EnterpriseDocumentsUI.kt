package com.fintechplatform.ui.enterprise.documents


import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.android.volley.toolbox.Volley
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.enterprise.documents.di.DaggerEnterpriseDocumentsViewComponent
import com.fintechplatform.ui.enterprise.documents.di.EnterpriseDocumentsPresenterModule
import com.fintechplatform.ui.enterprise.documents.di.EnterpriseDocumentsViewComponent
import com.fintechplatform.ui.models.DataAccount

class EnterpriseDocumentsUI(private val hostName: String, private val configuration: DataAccount) {

    fun startActivity(context: Context) {
        val intent = Intent(context, EnterpriseDocumentsActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment() : EnterpriseDocumentsFragment {
        return EnterpriseDocumentsFragment.newInstance(hostName, configuration)
    }

    object Builder {
        fun buildEnterpriseDocumentsViewComponent(context: Context, view: EnterpriseDocumentsContract.View, hostName: String, dataAccount: DataAccount): EnterpriseDocumentsViewComponent {
            return DaggerEnterpriseDocumentsViewComponent.builder()
                    .enterpriseAPIModule(EnterpriseAPIModule(hostName))
                    .enterpriseDocumentsPresenterModule(EnterpriseDocumentsPresenterModule(dataAccount, view))
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .build()
        }
    }
}
