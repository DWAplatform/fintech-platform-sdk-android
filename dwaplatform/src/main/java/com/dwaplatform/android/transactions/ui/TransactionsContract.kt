package com.dwaplatform.android.transactions.ui

import com.dwaplatform.android.transactions.models.TransactionItem


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