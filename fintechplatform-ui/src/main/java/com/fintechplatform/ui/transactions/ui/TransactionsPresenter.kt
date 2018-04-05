package com.fintechplatform.ui.transactions.ui

import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.transactions.api.TransactionsAPI
import com.fintechplatform.ui.transactions.db.TransactionPersistanceDB
import com.fintechplatform.ui.transactions.models.TransactionItem
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
        api.transactions(configuration.accessToken, configuration.ownerId, configuration.accountId, configuration.accountType, configuration.tenantId, 50, 0)
        { opttransactionsFull, opterror ->

            if (opterror != null) {
                handleErrors(opterror)
                return@transactions
            }

            opttransactionsFull?.let { transactionsFull ->
                dbTransactionsPHelper.saveAll(transactionsFull)
                currentTransactions()
            }
        }
    }

    override fun currentTransactions() {
        val trs = dbTransactionsPHelper.loadAll(configuration.accountId)
        view.showTransactions(trs)
    }

    override fun transactionClick(transaction: TransactionItem) {
        view.showTransactionDetail(transaction)
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpired()
            is NetHelper.GenericCommunicationError ->
                    view.showErrors(opterror.message)
            else -> return
        }
    }

}