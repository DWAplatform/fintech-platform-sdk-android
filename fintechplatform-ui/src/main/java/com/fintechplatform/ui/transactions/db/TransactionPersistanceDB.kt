package com.fintechplatform.ui.transactions.db

import android.util.Log
import com.fintechplatform.api.transactions.models.TransactionResponse
import com.fintechplatform.ui.transactions.models.ExternalTransaction
import com.fintechplatform.ui.transactions.models.TransactionHelper
import com.fintechplatform.ui.transactions.models.TransactionItem
import java.text.SimpleDateFormat
import javax.inject.Inject

class TransactionPersistanceDB @Inject constructor(val db: TransactionDB, val thelper: TransactionHelper){

    fun save(transaction: TransactionItem) {
        val t = Transaction(transaction.id,
                transaction.who,
                transaction.what,
                transaction.twhen,
                transaction.amount,
                transaction.order,
                transaction.status,
                "",
                transaction.message,
                transaction.error,
                transaction.accountId)

        return db.saveTransaction(t)
    }

    fun saveAll(transactionsFull: List<TransactionResponse>) {
        db.deleteTransactions()

        transactionsFull.forEach { t ->
            val optthitem = thelper.transactionItem(t)
            optthitem?.let { thitem ->
                save(thitem)
            }
        }
    }

    fun saveAllEsternal(transactionsFull: List<ExternalTransaction>) {
        db.deleteTransactions()

        val ordered = transactionsFull.sortedByDescending { t ->
            t.valueDate?.let {
                val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                try {
                    serverDateFormat.parse(it).time
                } catch (e: Exception) {
                    0L
                }
            }?: 0L
        }

        ordered.forEach { t ->
            val optthitem = thelper.convertTransactionItem(t)
            optthitem?.let { thitem ->
                save(thitem)
            }
        }
    }

    fun loadAll(accountId: String): List<TransactionItem> =

        db.getTransactions(accountId).mapNotNull { t ->

            try {
                val id = t.id?: throw IllegalArgumentException("id parameter required")
                val taccountId = t.accountId?: throw IllegalArgumentException("accountId parameter required")
                val what = t.what?: throw IllegalArgumentException("what parameter required")
                val who = t.who?: throw IllegalArgumentException("who parameter required")
                val amount = t.amount?: throw IllegalArgumentException("amount parameter required")
                val twhen = t.twhen?: throw IllegalArgumentException("when parameter required")
                val order = t.order?: throw IllegalArgumentException("order parameter required")
                val status = t.status?: throw IllegalArgumentException("status parameter required")

                TransactionItem(id, taccountId, what, who, t.message, amount, twhen, order, status, t.error)

            } catch (e: Throwable) {
                Log.e("Transaction parsing", e.message)
                null
            }
        }

}