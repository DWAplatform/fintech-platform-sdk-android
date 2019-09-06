package com.fintechplatform.ui.iban.db

import com.raizlabs.android.dbflow.sql.language.SQLite

class IbanDB {
    // TODO handle primary / secondary accountId
    fun findBankAccount(accountId: String): Iban? {
        return SQLite.select()
                .from(Iban::class.java)
                //.where(Iban_Table.id.eq(accountId))
                .querySingle()
    }

    fun saveBankAccount(iban: Iban) {
        iban.save()
    }

    //todo delete from accountId
    fun deleteBankAccount() {
        SQLite.delete().from(Iban::class.java).execute()
    }
}