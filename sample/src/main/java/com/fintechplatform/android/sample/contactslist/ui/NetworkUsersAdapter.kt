package com.fintechplatform.android.sample.contactslist.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fintechplatform.android.R
import com.fintechplatform.android.sample.contactslist.models.NetworkAccounts
import com.fintechplatform.android.sample.contactslist.models.NetworkAccountsManager
import com.fintechplatform.android.sample.contactslist.ui.networkuseritem.NetworkUserViewHolder

/**
 * Peer to Peer Payment List Adapter, visualize p2p items
 */
class NetworkUsersAdapter(val context: Context,
                          val manager: NetworkAccountsManager,
                          val p2puserClick: (NetworkAccounts) -> Unit)
    : RecyclerView.Adapter<NetworkUserViewHolder>() {

    override fun getItemCount(): Int {
        return manager.count()
    }

    override fun onBindViewHolder(holder: NetworkUserViewHolder?, position: Int) {
        holder?.bindForecast(manager.item(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NetworkUserViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.p2p_item, parent, false)
        val vh = NetworkUserViewHolder(view, p2puserClick)
        //(context.applicationContext as App).netComponent?.inject(vh)
        return vh
    }

}

