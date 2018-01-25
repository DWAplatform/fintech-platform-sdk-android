package com.fintechplatform.android.transactions.ui

import com.fintechplatform.android.transactions.models.TransactionItem


/**
 * Transaction View/Presenter Contract
 */
interface TransactionsContract {

    interface View {
        fun showTransactions(trs: List<TransactionItem>)
        fun showTransactionDetail(transaction: TransactionItem)
        fun showTokenExpired()
    }

    interface Presenter {
        fun refreshTransactions()
        fun transactionClick(transaction: TransactionItem)
        fun currentTransactions()
    }
}