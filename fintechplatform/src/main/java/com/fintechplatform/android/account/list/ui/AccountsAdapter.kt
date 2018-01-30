package com.fintechplatform.android.account.list.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fintechplatform.android.R
import com.fintechplatform.android.account.list.models.AccountItem
import com.fintechplatform.android.account.list.models.AccountsManager

class AccountsAdapter constructor(val context: Context,
                                  val manager: AccountsManager,
                                  val userClick: (AccountItem) -> Unit): RecyclerView.Adapter<AccountsViewHolder>() {

    override fun onBindViewHolder(holder: AccountsViewHolder?, position: Int) {
        holder?.bindForcast(manager.item(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AccountsViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cardview_accountitem, parent, false)
        return AccountsViewHolder(view, userClick)
    }

    override fun getItemCount(): Int {
        return manager.count()
    }
}