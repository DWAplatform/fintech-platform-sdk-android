package com.dwafintech.dwapay.main.networklist.ui.networkuseritem


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