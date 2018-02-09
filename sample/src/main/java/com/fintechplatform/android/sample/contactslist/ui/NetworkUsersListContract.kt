package com.fintechplatform.android.sample.contactslist.ui

import com.fintechplatform.android.sample.contactslist.models.NetworkUserModel

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