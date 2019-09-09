package com.fintechplatform.ui.transactions.db

import com.raizlabs.android.dbflow.sql.language.SQLite


class TransactionDB {

    fun deleteTransactions() {
        SQLite.delete().from(Transaction::class.java).execute()
    }

    fun saveTransaction(t: Transaction) {
        t.save()
    }

    fun getTransactions(accountId: String): List<Transaction> =
            SQLite.select()
                .from(Transaction::class.java)
                .where(Transaction_Table.accountId.eq(accountId))
                .queryList()

}