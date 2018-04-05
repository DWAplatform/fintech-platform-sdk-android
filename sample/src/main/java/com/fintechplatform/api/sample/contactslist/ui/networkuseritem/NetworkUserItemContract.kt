package com.fintechplatform.api.sample.contactslist.ui.networkuseritem

import com.fintechplatform.api.sample.contactslist.models.NetworkAccounts


interface NetworkUserItemContract {
    interface View {
        fun setP2PCategory()
        fun setBankCategory()
        fun setUserFullName(name: String)
        fun setUserImage(photo: String)
    }

    interface Presenter {
        fun initializate(p2puser: NetworkAccounts)
    }
}