package com.fintechplatform.android.sample.contactslist.ui.networkuseritem

import com.fintechplatform.android.sample.contactslist.db.NetworkUsersPersistance
import com.fintechplatform.android.sample.contactslist.models.NetworkUserModel


class NetworkUserItemPresenter constructor(val view: NetworkUserItemContract.View,
                                           val dbNetworkUsersHelper: NetworkUsersPersistance) : NetworkUserItemContract.Presenter {

    override fun initializate(p2puser: NetworkUserModel) {
        view.setUserFullName(dbNetworkUsersHelper.getFullName(p2puser))
        view.setUserImage(p2puser.photo.orEmpty())


        when(p2puser.type) {
            "BANK" -> view.setBankCategory()
            "NetworkUsers" -> view.setP2PCategory()
            else -> return
        }
    }
}