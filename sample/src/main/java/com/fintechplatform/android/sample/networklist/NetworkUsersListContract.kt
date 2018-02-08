package com.dwafintech.dwapay.main.networklist

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