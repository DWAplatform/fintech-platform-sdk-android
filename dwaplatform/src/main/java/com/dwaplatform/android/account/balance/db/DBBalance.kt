package com.dwaplatform.android.account.balance.db

import com.raizlabs.android.dbflow.sql.language.SQLite

class DBBalance {
    fun deleteBalance() {
        SQLite.delete().from(Balance::class.java).execute()
    }

    fun saveBalance(balance: Balance) {
        balance.save()
    }

    fun findBalance(accountId: String) : Balance? {
        // TODO search accountId
        return SQLite.select().from(Balance::class.java).querySingle()
    }
}
