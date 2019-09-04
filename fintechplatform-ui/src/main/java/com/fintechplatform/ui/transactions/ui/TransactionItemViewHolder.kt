package com.fintechplatform.ui.transactions.ui

import android.annotation.TargetApi
import android.graphics.Color
import android.graphics.drawable.TransitionDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.fintechplatform.ui.R
import com.fintechplatform.ui.transactions.models.TransactionItem
import com.fintechplatform.ui.transactions.ui.itemview.TransactionItemContract
import kotlinx.android.synthetic.main.transaction_minimalui_item.view.*
import javax.inject.Inject

/**
 * Transaction Item View Holder
 */
class TransactionItemViewHolder @Inject constructor(val view: View,
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

    override fun newItemVisible() {
        itemView.itemlist_container.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.half_grey))

        itemView.itemlist_container.setBackgroundResource(R.drawable.translate_background_listitem)
        val transition = itemView.itemlist_container.background as TransitionDrawable
        transition.startTransition(5000)
    }

    override fun newItemHide() {
        itemView.itemlist_container.setBackgroundColor(Color.WHITE)
    }

    override fun setPositiveAmount(amount: String) {
        itemView.listitem_amount.text = amount
        itemView.listitem_amount.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.moneyGreen))
    }

    override fun setNegativeAmount(amount: String) {
        itemView.listitem_amount.text = amount
        itemView.listitem_amount.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.moneyRed))
    }

    override fun setTemporaryValue(amount: String) {
        itemView.listitem_amount.text = amount
        itemView.listitem_amount.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.moneyTemp)
        )
    }

    override fun setErrorAmount(amount: String) {
        itemView.listitem_amount.text = amount
        itemView.listitem_amount.setTextColor(
                ContextCompat.getColor(itemView.context, R.color.grayLight))
    }

    override fun showError() {
        itemView.listitem_error.visibility = View.VISIBLE

        @TargetApi(16)
        itemView.listitem_icon_image.imageAlpha = 50
    }

    override fun hideError() {
        itemView.listitem_error.visibility = View.GONE
    }

    override fun setIconCashIn() {
        itemView.listitem_icon_image.setImageResource(R.drawable.cashin)
    }

    override fun setIconCashOut() {
        itemView.listitem_icon_image.setImageResource(R.drawable.cashout)
    }

    override fun setIconP2P() {
        itemView.listitem_icon_image.setImageResource(R.drawable.p2p)
    }

}