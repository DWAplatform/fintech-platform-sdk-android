package com.fintechplatform.ui.transactions.db

import com.fintechplatform.ui.transactions.models.TransactionItem
import com.fintechplatform.api.transactions.models.TransactionResponse
import javax.inject.Inject

class TransactionPersistanceDB @Inject constructor(val db: TransactionDB, val thelper: TransactionHelper){

    fun save(transaction: TransactionItem) {
        val t = Transaction()
        t.setWho(transaction.who)
        t.setWhat(transaction.what)
        t.setTwhen(transaction.twhen)
        t.setAmount(transaction.amount)
        t.setOrder(transaction.order)
        t.setStatus(transaction.status)
        t.setId(transaction.id)
        t.setMessage(transaction.message)
        t.setError(transaction.error)
        t.accountId = transaction.accountId

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

    fun loadAll(accountId: String): List<TransactionItem> {
        val ts = db.getTransactions(accountId)
        return ts.map { t ->
            TransactionItem(t.id, t.accountId, t.what, t.who, t.message, t.amount, t.twhen, t.order, t.status, t.error)
        }
    }

}