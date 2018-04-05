package com.fintechplatform.api.sample.contactslist.ui.networkuseritem

import com.fintechplatform.api.sample.contactslist.db.NetworkUsersPersistance
import com.fintechplatform.api.sample.contactslist.models.NetworkAccounts


class NetworkUserItemPresenter constructor(val view: NetworkUserItemContract.View,
                                           val dbNetworkUsersHelper: NetworkUsersPersistance) : NetworkUserItemContract.Presenter {

    override fun initializate(p2puser: NetworkAccounts) {
        view.setUserFullName(dbNetworkUsersHelper.getFullName(p2puser))
        view.setUserImage(p2puser.photo.orEmpty())
//
//        FIXME wait server implementation
//        when(p2puser.accountType) {
//            "BANK" -> view.setBankCategory()
//            "NetworkUsers" -> view.setP2PCategory()
//            else -> return
//        }
    }
}