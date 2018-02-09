package com.fintechplatform.android.sample.contactslist.ui

import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.sample.contactslist.api.PeersAPI
import com.fintechplatform.android.sample.contactslist.db.NetworkUsersPersistance


class NetworkUsersPresenter constructor(val view: NetworkUsersListContract.View,
                                        val api: PeersAPI,
                                        val config: DataAccount,
                                        val networkUsersPersistance: NetworkUsersPersistance): NetworkUsersListContract.Presenter {
    override fun onRefresh() {
        refreshP2P()
        reloadP2P()
    }

    override fun reloadP2P() {
        // fetchContacts()
        api.p2pPeersFiltered(config.accessToken, config.userId, config.accountId, config.tenantId) { optp2pusers, opterror ->

            view.enableRefreshing(false)
            if (opterror != null) {
                handleErrors(opterror)
                return@p2pPeersFiltered
            }

            optp2pusers?.let { p2pusers ->
                networkUsersPersistance.saveAll(p2pusers)
                refreshP2P()
            }
        }
    }

    override fun refreshP2P() {
        view.initAdapter(networkUsersPersistance.loadAll())
        view.refreshAdapter()
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                view.showCommunicationInternalError()
        }
    }
}