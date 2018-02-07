package com.fintechplatform.android.transfer.contactslist.ui.networkuseritem

import com.fintechplatform.android.transfer.contactslist.models.NetworkUserModel


interface NetworkUserItemContract {
    interface View {
        fun setP2PCategory()
        fun setBankCategory()
        fun setUserFullName(name: String)
        fun setUserImage(photo: String)
    }

    interface Presenter {
        fun initializate(p2puser: NetworkUserModel)
    }
}