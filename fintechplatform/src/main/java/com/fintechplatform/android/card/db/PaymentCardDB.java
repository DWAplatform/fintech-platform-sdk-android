package com.fintechplatform.android.card.db;

import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by ingrid on 01/03/18.
 */

public class PaymentCardDB {

    public void deletePaymentCard() {
        SQLite.delete().from(PaymentCard.class).execute();
    }

    public void savePaymentCard(PaymentCard card) {
        card.save();
    }

    public PaymentCard findPaymentCard(String accountId) {
        return SQLite.select()
                .from(PaymentCard.class)
                .where(PaymentCard_Table.accountId.eq(accountId))
                .querySingle();
    }
}
