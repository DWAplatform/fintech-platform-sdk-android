package com.fintechplatform.android.transfer.contactslist.ui

import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.transfer.api.TransferAPI
import com.fintechplatform.android.transfer.contactslist.db.NetworkUsersPersistance
import java.util.*


class NetworkUsersPresenter constructor(val view: NetworkUsersListContract.View,
                                        val api: TransferAPI,
                                        val config: DataAccount,
                                        val networkUsersPersistance: NetworkUsersPersistance): NetworkUsersListContract.Presenter {
    override fun onRefresh() {
        refreshP2P()
        reloadP2P()
    }

    override fun reloadP2P() {
        val telnumbers = ArrayList<String>() // fetchContacts()
        api.p2pPeersFiltered(config.accessToken, config.userId, telnumbers) { optp2pusers, opterror ->

            view.enableRefreshing(false)
            if (opterror != null)
                return@p2pPeersFiltered

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


}