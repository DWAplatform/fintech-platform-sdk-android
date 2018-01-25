package com.dwaplatform.android.transactions.ui.detail.ui

import com.dwaplatform.android.transactions.models.TransactionItem

interface TransactionDetailContract {
    interface View{
        fun sendEmail(transaction: TransactionItem)
        fun showMessageContent(msg: String)
        fun setSource(who: String?)

        fun setTransactionWhat(what: String?)
        fun setTransactionAmount(amount: String?)
        fun setTransactionTwhen(twhen: String?)
        fun setTransactionStateDescription(stateDescription: String)
        fun setTransactionResultDescription(resultDescription: String)
    }

    interface Presenter{
        fun onHelp()
        fun initialize(transaction: TransactionItem?)
    }
}