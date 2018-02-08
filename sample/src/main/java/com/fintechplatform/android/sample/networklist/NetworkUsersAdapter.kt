package com.dwafintech.dwapay.main.networklist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dwafintech.dwapay.main.networklist.ui.networkuseritem.NetworkUserViewHolder
import com.fintechplatform.android.R

/**
 * Peer to Peer Payment List Adapter, visualize p2p items
 */
class NetworkUsersAdapter(val context: Context,
                          val manager: NetworkUsersManager,
                          val p2puserClick: (NetworkUserModel) -> Unit)
    : RecyclerView.Adapter<NetworkUserViewHolder>() {

    override fun getItemCount(): Int {
        return manager.count()
    }

    override fun onBindViewHolder(holder: NetworkUserViewHolder?, position: Int) {
        holder?.bindForecast(manager.item(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NetworkUserViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.network_user_item, parent, false)
        val vh = NetworkUserViewHolder(view, p2puserClick)
        //(context.applicationContext as App).netComponent?.inject(vh)
        return vh
    }

}

