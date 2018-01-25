package com.fintechplatform.android.transactions.db

import com.fintechplatform.android.transactions.models.TransactionItem
import com.fintechplatform.android.transactions.models.TransactionResponse
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
        t.setResultcode(transaction.resultcode)
        t.setId(transaction.id)
        t.setMessage(transaction.message)

        return db.saveTransaction(t)
    }

    fun saveAll(transactionsFull: List<TransactionResponse>, userid: String) {
        db.deleteTransactions()

        transactionsFull.forEach { t ->
            val optthitem = thelper.transactionItem(t, userid)
            optthitem?.let { thitem ->
                save(thitem)
            }
        }
    }

    fun loadAll(): List<TransactionItem> {
        val ts = db.getTransactions()
        return ts.map { t ->
            TransactionItem(t.id, t.what, t.who, t.message, t.amount, t.twhen, t.order, t.status, t.resultcode)
        }
    }

}