package com.dwaplatform.android.transactions.db

import com.raizlabs.android.dbflow.sql.language.SQLite

class TransactionDB {
    fun deleteTransactions() {
        SQLite.delete().from(Transaction::class.java).execute()
    }

    fun saveTransaction(t: Transaction) {
        t.save()
    }

    fun getTransactions(): List<Transaction> {
        return SQLite.select().from(Transaction::class.java).queryList()
    }
}