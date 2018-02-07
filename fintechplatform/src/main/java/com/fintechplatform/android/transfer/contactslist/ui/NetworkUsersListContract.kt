package com.fintechplatform.android.transfer.contactslist.ui

import com.fintechplatform.android.transfer.contactslist.models.NetworkUserModel

interface NetworkUsersListContract {
    interface View {
        fun checkPermissions()
        fun enableRefreshing(isRefreshing: Boolean)
        fun initAdapter(networkUserModels: List<NetworkUserModel>)
        fun refreshAdapter()
    }

    interface Presenter {
        fun onRefresh()
        fun reloadP2P()
        fun refreshP2P()
    }
}