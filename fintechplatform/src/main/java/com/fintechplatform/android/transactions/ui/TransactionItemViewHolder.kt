package com.fintechplatform.android.transactions.ui

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.fintechplatform.android.R
import com.fintechplatform.android.transactions.models.TransactionItem
import com.fintechplatform.android.transactions.ui.itemview.TransactionItemContract
import kotlinx.android.synthetic.main.transaction_item.view.*
import javax.inject.Inject

/**
 * Transaction Item View Holder
 */
class TransactionItemViewHolder
@Inject constructor(val view: View,
                    val tUserClick: (TransactionItem) -> Unit)
    : RecyclerView.ViewHolder(view), TransactionItemContract.View {

    @Inject lateinit var presenter: TransactionItemContract.Presenter

    init {
        TransactionsUI.instance.buildTransactionItemComponent(this).inject(this)
    }

    fun bindForecast(item: TransactionItem) {
        presenter.item(item)
        itemView.setOnClickListener { presenter.userClick(item, tUserClick) }
    }

    override fun setTextWhen(w: String) {
        itemView.listitem_when.text = w
    }

    override fun setTextWhat(w: String) {
        itemView.listitem_what.text = w
    }

    override fun setTextWho(w: String) {
        itemView.listitem_who.text = w
    }

    override fun newItemVisible() {
        itemView.listitem_newitem.visibility = View.VISIBLE
    }

    override fun newItemHide() {
        itemView.listitem_newitem.visibility = View.GONE
    }

    override fun setPositiveAmount(amount: String) {
        itemView.listitem_amount.text = amount
        itemView.listitem_amount.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.dwalogoMoneyGreen))
    }

    override fun setNegativeAmount(amount: String) {
        itemView.listitem_amount.text = amount
        itemView.listitem_amount.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.dwalogoMoneyRed))
    }

    override fun setErrorAmount(amount: String) {
        itemView.listitem_amount.text = amount
        itemView.listitem_amount.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.dwaGrayLight))
    }

    override fun showError() {
        itemView.listitem_error.visibility = View.VISIBLE
    }

    override fun hideError() {
        itemView.listitem_error.visibility = View.GONE
    }


}