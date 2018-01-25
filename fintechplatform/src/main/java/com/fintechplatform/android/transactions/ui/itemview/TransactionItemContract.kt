package com.fintechplatform.android.transactions.ui.itemview

import com.fintechplatform.android.transactions.models.TransactionItem

/**
 * Transaction Item View/Presenter Contract
 */
interface TransactionItemContract {

    interface View {
        fun setTextWhen(w: String)
        fun setTextWhat(w: String)
        fun setTextWho(w: String)
        fun newItemVisible()
        fun newItemHide()
        fun setPositiveAmount(amount: String)
        fun setNegativeAmount(amount: String)
        fun setErrorAmount(amount: String)
        fun showError()
        fun hideError()
    }

    interface Presenter {
        fun item(item: TransactionItem)
        fun userClick(item: TransactionItem, tuserClick: (TransactionItem) -> Unit)
    }
}