package com.fintechplatform.ui.transactions.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fintechplatform.ui.R
import com.fintechplatform.ui.transactions.models.TransactionItem
import com.fintechplatform.ui.transactions.models.TransactionsManager

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
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.transaction_minimalui_item, parent, false)
        val tiviewholder = TransactionItemViewHolder(view, tuserClick)
        return tiviewholder
    }

}