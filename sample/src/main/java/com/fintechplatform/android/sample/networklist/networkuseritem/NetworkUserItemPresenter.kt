package com.dwafintech.dwapay.main.networklist.ui.networkuseritem

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