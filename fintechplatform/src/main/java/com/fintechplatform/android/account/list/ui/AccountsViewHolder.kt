package com.fintechplatform.android.account.list.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import com.fintechplatform.android.account.list.models.AccountItem
import kotlinx.android.synthetic.main.cardview_accountitem.view.*
import javax.inject.Inject


class AccountsViewHolder constructor(val view: View,
                                     val userClick: (AccountItem) -> Unit): RecyclerView.ViewHolder(view), AccountsItemContract.View {

    @Inject lateinit var presenter: AccountsItemContract.Presenter

    init {
        // accountsItemUI.injection
    }

    fun bindForcast(item: AccountItem) {
        presenter.item(item)
        itemView.setOnClickListener { presenter.userClick(item, userClick) }
    }

    override fun setAccountNameText(account: String) {
        itemView.accountName.text = account
    }

    override fun setAccountIcon() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}