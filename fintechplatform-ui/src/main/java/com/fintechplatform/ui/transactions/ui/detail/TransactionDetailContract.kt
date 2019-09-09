package com.fintechplatform.ui.transactions.ui.detail

import com.fintechplatform.ui.transactions.models.TransactionItem

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