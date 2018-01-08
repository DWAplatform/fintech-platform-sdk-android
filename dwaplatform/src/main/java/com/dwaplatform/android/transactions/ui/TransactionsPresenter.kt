package com.dwaplatform.android.transactions.ui

import com.dwaplatform.android.auth.keys.KeyChain
import com.dwaplatform.android.models.DataAccount
import com.dwaplatform.android.transactions.api.TransactionsAPI
import com.dwaplatform.android.transactions.db.TransactionPersistanceDB
import com.dwaplatform.android.transactions.models.TransactionItem
import javax.inject.Inject

/**
 * Transactions list presenter
 */
open class TransactionsPresenter @Inject constructor(val view: TransactionsContract.View,
                                                     val api: TransactionsAPI,
                                                     val configuration: DataAccount,
                                                     val key: KeyChain,
                                                     val dbTransactionsPHelper: TransactionPersistanceDB)
    : TransactionsContract.Presenter {

    override fun refreshTransactions() {
        api.transactions(key["tokenuser"], configuration.userId, 50, 0)
        { opttransactionsFull, opterror ->

            if (opterror != null)
                return@transactions

            opttransactionsFull?.let { transactionsFull ->
                dbTransactionsPHelper.saveAll(transactionsFull, configuration.userId)
                currentTransactions()
            }
        }
    }

    override fun currentTransactions() {
        val trs = dbTransactionsPHelper.loadAll()
        view.showTransactions(trs)
    }

    override fun transactionClick(transaction: TransactionItem) {
        view.showTransactionDetail(transaction)

    }

}