package com.fintechplatform.ui.profile.contacts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.contacts.di.ContactsPresenterModule
import com.fintechplatform.ui.profile.contacts.di.ContactsViewComponent
import com.fintechplatform.ui.profile.contacts.di.DaggerContactsViewComponent

class ContactsUI(private val hostName: String, private val configuration: DataAccount) {

    fun startActivity(context: Context) {
        val intent = Intent(context, ContactsActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun creatFragment(hostName: String, dataAccount: DataAccount): ContactsFragment {
        return ContactsFragment.newInstance(hostName, dataAccount)
    }

    object Builder {
        fun buildContactsViewComponet(context: Context, view: ContactsContract.View, hostName: String, dataAccount: DataAccount): ContactsViewComponent {
            return DaggerContactsViewComponent.builder()
                    .netModule(NetModule(Volley.newRequestQueue(context), hostName))
                    .contactsPresenterModule(ContactsPresenterModule(view, dataAccount))
                    .profileAPIModule(ProfileAPIModule(hostName))
                    .idsDocumentsAPIModule(IdsDocumentsAPIModule(hostName))
                    .build()
        }

    }
}
