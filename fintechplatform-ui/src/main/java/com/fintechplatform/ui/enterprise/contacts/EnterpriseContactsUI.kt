package com.fintechplatform.ui.enterprise.contacts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.enterprise.contacts.di.DaggerEnterpriseContactsViewComponent
import com.fintechplatform.ui.enterprise.contacts.di.EnterpriseContactsPresenterModule
import com.fintechplatform.ui.enterprise.contacts.di.EnterpriseContactsViewComponent
import com.fintechplatform.ui.models.DataAccount

class EnterpriseContactsUI(private val hostName: String, private val configuration: DataAccount) {

    fun startActivity(context: Context) {
        val intent = Intent(context, EnterpriseContactsActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment(): EnterpriseContactsFragment {
        return EnterpriseContactsFragment.newInstance(hostName, configuration)
    }

    object Builder {
        fun buildContactsViewComponet(context: Context, view: EnterpriseContactsContract.View, hostName: String, dataAccount: DataAccount): EnterpriseContactsViewComponent {
            return DaggerEnterpriseContactsViewComponent.builder()
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .enterpriseContactsPresenterModule(EnterpriseContactsPresenterModule(view, dataAccount))
                    .enterpriseAPIModule(EnterpriseAPIModule(hostName))
                    .build()
        }
    }
}