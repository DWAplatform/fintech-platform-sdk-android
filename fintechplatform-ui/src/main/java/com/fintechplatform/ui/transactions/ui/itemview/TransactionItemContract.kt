package com.fintechplatform.ui.transactions.ui.itemview

import com.fintechplatform.ui.transactions.models.TransactionItem

/**
 * Transaction Item View/Presenter Contract
 */
interface TransactionItemContract {

    interface View {
        fun setTextWhen(w: String)
        fun setTextWhat(w: String)
        fun newItemVisible()
        fun newItemHide()
        fun setTemporaryValue(amount: String)
        fun setPositiveAmount(amount: String)
        fun setNegativeAmount(amount: String)
        fun setErrorAmount(amount: String)
        fun showError()
        fun hideError()
        fun setIconCashIn()
        fun setIconCashOut()
        fun setIconP2P()
    }

    interface Presenter {
        fun item(item: TransactionItem)
        fun userClick(item: TransactionItem, tuserClick: (TransactionItem) -> Unit)
    }
}