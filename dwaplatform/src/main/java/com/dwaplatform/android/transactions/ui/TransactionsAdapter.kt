package com.dwaplatform.android.transactions.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dwaplatform.android.R
import com.dwaplatform.android.transactions.models.TransactionItem
import com.dwaplatform.android.transactions.models.TransactionsManager
import com.dwaplatform.android.transactions.ui.transactionItemView.TransactionItemUI
import com.dwaplatform.android.transactions.ui.transactionItemView.TransactionItemViewHolder

/**
 * Transactions List Adapter for visualize transaction items
 */
class TransactionsAdapter(val context: Context,
                          val manager: TransactionsManager,
                          val tuserClick: (TransactionItem) -> Unit)
    : RecyclerView.Adapter<TransactionItemViewHolder>() {

    override fun getItemCount(): Int {
        return manager.count()
    }

    override fun onBindViewHolder(holder: TransactionItemViewHolder?, position: Int) {
        holder?.bindForecast(manager.item(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TransactionItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.transaction_item, parent, false)
        val tiviewholder = TransactionItemViewHolder(view, tuserClick)
        TransactionItemUI().getInstance()
        return tiviewholder
    }

}