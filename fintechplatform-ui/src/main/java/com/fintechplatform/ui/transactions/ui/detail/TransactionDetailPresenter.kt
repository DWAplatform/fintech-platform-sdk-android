package com.fintechplatform.ui.transactions.ui.detail

import com.fintechplatform.ui.transactions.models.TransactionDetailHelper
import com.fintechplatform.ui.transactions.models.TransactionItem

class TransactionDetailPresenter constructor(var view: TransactionDetailContract.View,
                                             var transactionDetailHelper: TransactionDetailHelper): TransactionDetailContract.Presenter {

    var transaction: TransactionItem? = null

    override fun initialize(transaction: TransactionItem?) {
        this.transaction = transaction
        view.setSource(transaction?.who)

        transaction?.message?.let { msg ->
            if (msg.isNotEmpty()) {
                view.showMessageContent(msg)
            }
        }

        view.setTransactionWhat(transaction?.what)
        view.setTransactionAmount(transaction?.amount)
        view.setTransactionTwhen(transaction?.twhen)
        view.setTransactionStateDescription(transactionDetailHelper.stateDescription(transaction?.status))

    }

    override fun onHelp() {
        val transaction = this.transaction ?: return
        view.sendEmail(transaction)
    }
}