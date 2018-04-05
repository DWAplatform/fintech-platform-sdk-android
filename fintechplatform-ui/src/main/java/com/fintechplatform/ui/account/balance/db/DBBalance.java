package com.fintechplatform.ui.account.balance.db;

import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by ingrid on 13/02/18.
 */

public class DBBalance {
    public void deleteBalance() {
        SQLite.delete().from(Balance.class).execute();
    }

    public void saveBalance(Balance balance) {
        balance.save();
    }

    public Balance findBalance(String accountId) {
        return SQLite.select()
                .from(Balance.class)
                .where(Balance_Table.accountId.eq(accountId))
                .querySingle();
    }
}
