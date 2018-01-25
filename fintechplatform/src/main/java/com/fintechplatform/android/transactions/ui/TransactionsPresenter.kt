package com.fintechplatform.android.transactions.ui

import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.transactions.api.TransactionsAPI
import com.fintechplatform.android.transactions.db.TransactionPersistanceDB
import com.fintechplatform.android.transactions.models.TransactionItem
import javax.inject.Inject

/**
 * Transactions list presenter
 */
open class TransactionsPresenter @Inject constructor(val view: TransactionsContract.View,
                                                     val api: TransactionsAPI,
                                                     val configuration: DataAccount,
                                                     val dbTransactionsPHelper: TransactionPersistanceDB)
    : TransactionsContract.Presenter {

    var token:String?=null

    override fun refreshTransactions() {
        api.transactions(configuration.accessToken, configuration.userId, 50, 0)
        { opttransactionsFull, opterror ->

            if (opterror != null) {
                handleErrors(opterror)
                return@transactions
            }

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

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpired()
            else -> return
        }
    }

}