package com.fintechplatform.api.sample.contactslist.ui.networkuseritem

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.fintechplatform.api.sample.contactslist.models.NetworkAccounts
import com.fintechplatform.api.sample.contactslist.ui.NetworkUsersListUI
import kotlinx.android.synthetic.main.p2p_item.view.*
import javax.inject.Inject


class NetworkUserViewHolder(view: View, val p2puserClick: (NetworkAccounts) -> Unit) : RecyclerView.ViewHolder(view), NetworkUserItemContract.View {

    @Inject lateinit var imageHelper: ImageHelper
    @Inject lateinit var presenter: NetworkUserItemContract.Presenter

    init {
        NetworkUsersListUI.instace.buildUserViewHolderComponent(this).inject(this)
    }

    fun bindForecast(p2puser: NetworkAccounts) {
        presenter.initializate(p2puser)
        itemView.setOnClickListener { p2puserClick(p2puser) }
    }

    override fun setUserImage(photo: String) {
        imageHelper.setImageViewUser(itemView.networkitem_icon, photo)
    }

    override fun setUserFullName(name: String) {
        itemView.networkitem_name.text = name
    }

    override fun setP2PCategory() {
        itemView.listitem_category.visibility = View.VISIBLE
        //itemView.listitem_category.setBackgroundResource(R.drawable.background_category_yellow)
        itemView.listitem_category.text = "NetworkUsers"
        itemView.listitem_category.setTextColor(Color.BLACK)
    }

    override fun setBankCategory() {
        itemView.listitem_category.visibility = View.VISIBLE
        //listitem_category.setBackgroundResource(R.drawable.background_category_blue)
        itemView.listitem_category.text = "IBAN"
        itemView.listitem_category.setTextColor(Color.BLACK)
    }
}