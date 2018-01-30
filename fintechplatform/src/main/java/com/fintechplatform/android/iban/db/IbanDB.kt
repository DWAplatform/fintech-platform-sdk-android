package com.fintechplatform.android.iban.db

import com.raizlabs.android.dbflow.sql.language.SQLite

class IbanDB {
    fun deleteBankAccount() {
        SQLite.delete().from(Iban::class.java).execute()
    }

    fun saveBankAccount(ibans: Iban) {
        ibans.save()
    }

    fun findBankAccount() : Iban? {
        return SQLite.select().from(Iban::class.java).querySingle()
    }
}