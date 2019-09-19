package com.fintechplatform.ui.profile.address

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.address.di.AddressPresenterModule
import com.fintechplatform.ui.profile.address.di.AddressViewComponent
import com.fintechplatform.ui.profile.address.di.DaggerAddressViewComponent


class AddressUI(private val hostName: String, private val dataAccount: DataAccount) {

//
//    fun createAddressComponent(context: Context, view: AddressContract.View): AddressViewComponent {
//        return buildAddressComponent(context, view)
//    }

    fun startActivity(context: Context) {
        val intent = Intent(context, AddressActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", dataAccount)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment(hostName: String, dataAccount: DataAccount): AddressFragment {
        return AddressFragment.newInstance(hostName, dataAccount)
    }
    object Builder {
        fun buildAddressComponent(context: Context, view: AddressContract.View, hostName: String, dataAccount: DataAccount): AddressViewComponent {
            return DaggerAddressViewComponent.builder()
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .profileAPIModule(ProfileAPIModule(hostName))
                    .idsDocumentsAPIModule(IdsDocumentsAPIModule(hostName))
                    .addressPresenterModule(AddressPresenterModule(view, dataAccount))
                    .build()

        }
    }
}
