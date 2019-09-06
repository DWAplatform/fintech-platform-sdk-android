package com.fintechplatform.ui.iban

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.iban.api.IbanAPIModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.iban.di.DaggerIBANViewComponent
import com.fintechplatform.ui.iban.di.IBANPresenterModule
import com.fintechplatform.ui.iban.di.IBANViewComponent
import com.fintechplatform.ui.models.DataAccount


class IbanUI {

    fun startActivity(context: Context, hostname: String, dataAccount: DataAccount) {
        val intent = Intent(context, IBANActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostname)
        args.putParcelable("dataAccount", dataAccount)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun startFragment(hostname: String, dataAccount: DataAccount): IBANFragment {
        return IBANFragment.newInstance(hostname, dataAccount)
    }

    object Builder {
        /**
         * Resolve with default decendencies for iban view component
         */
        fun buildIbanViewComponent(context: Context, view: IBANContract.View, hostname: String, configuration: DataAccount): IBANViewComponent {
            return DaggerIBANViewComponent.builder()
                    .netModule(NetModule(Volley.newRequestQueue(context), hostname))
                    .iBANPresenterModule(IBANPresenterModule(view, configuration))
                    .ibanAPIModule(IbanAPIModule(hostname))
                    .profileAPIModule(ProfileAPIModule(hostname))
                    .enterpriseAPIModule(EnterpriseAPIModule(hostname))
                    .build()
        }
    }
}