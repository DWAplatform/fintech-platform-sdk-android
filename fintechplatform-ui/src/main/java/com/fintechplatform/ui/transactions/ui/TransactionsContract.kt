package com.fintechplatform.ui.transactions.ui

import com.fintechplatform.ui.transactions.models.TransactionItem


/**
 * Transaction View/Presenter Contract
 */
interface TransactionsContract {

    interface View {
        fun showTransactions(trs: List<TransactionItem>)
        fun showTransactionDetail(transaction: TransactionItem)
        fun showTokenExpired()
        fun showErrors(message: String?)
    }

    interface Presenter {
        fun refreshTransactions()
        fun transactionClick(transaction: TransactionItem)
        fun currentTransactions()
    }
}