package com.fintechplatform.api.sample.contactslist.ui

import com.fintechplatform.api.sample.contactslist.models.NetworkAccounts

interface NetworkUsersListContract {
    interface View {
        fun checkPermissions()
        fun enableRefreshing(isRefreshing: Boolean)
        fun initAdapter(networkAccounts: List<NetworkAccounts>)
        fun refreshAdapter()
        fun showCommunicationInternalError()
        fun showTokenExpiredWarning()
    }

    interface Presenter {
        fun onRefresh()
        fun reloadP2P()
        fun refreshP2P()
    }
}