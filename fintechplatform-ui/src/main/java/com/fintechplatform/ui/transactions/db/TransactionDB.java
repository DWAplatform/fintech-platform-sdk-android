package com.fintechplatform.ui.transactions.db;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by ingrid on 01/03/18.
 */

public class TransactionDB {

    public void deleteTransactions() {
        SQLite.delete().from(Transaction.class).execute();
    }

    public void saveTransaction(Transaction t){
        t.save();
    }

    public List<Transaction> getTransactions(String accountId) {
        return SQLite.select()
                .from(Transaction.class)
                .where(Transaction_Table.accountId.eq(accountId))
                .queryList();
    }
}
