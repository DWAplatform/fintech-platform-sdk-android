package com.fintechplatform.android.iban.db;

import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by ingrid on 01/03/18.
 */

public class IbanDB {
    // TODO handle primary / secondary accountId
    public Iban findBankAccount(String accountId) {
        return SQLite.select()
                .from(Iban.class)
//                .where(Iban_Table.id.eq(accountId))
                .querySingle();
    }

    public void saveBankAccount(Iban iban) {
        iban.save();
    }

    //todo delete from accountId
    public void deleteBankAccount() {
        SQLite.delete().from(Iban.class).execute();
    }
}
