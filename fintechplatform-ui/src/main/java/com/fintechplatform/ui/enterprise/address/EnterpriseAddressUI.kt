package com.fintechplatform.ui.enterprise.address

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.enterprise.address.di.DaggerEnterpriseAddressViewComponent
import com.fintechplatform.ui.enterprise.address.di.EnterpriseAddressPresenterModule
import com.fintechplatform.ui.enterprise.address.di.EnterpriseAddressViewComponent
import com.fintechplatform.ui.models.DataAccount


class EnterpriseAddressUI(private val hostName: String, private val dataAccount: DataAccount) {

    fun startActivity(context: Context) {
        val intent = Intent(context, EnterpriseAddressActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", dataAccount)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment(): EnterpriseAddressFragment {
        return EnterpriseAddressFragment.newInstance(hostName, dataAccount)
    }

    object Builder {
        fun buildAddressComponent(context: Context, view: EnterpriseAddressContract.View, hostName: String, dataAccount: DataAccount): EnterpriseAddressViewComponent {
            return DaggerEnterpriseAddressViewComponent.builder()
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .enterpriseAPIModule(EnterpriseAPIModule(hostName))
                    .enterpriseAddressPresenterModule(EnterpriseAddressPresenterModule(view, dataAccount))
                    .build()

        }
    }
}
