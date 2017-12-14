package com.dwaplatform.android.card.db

import com.raizlabs.android.dbflow.sql.language.SQLite

/**
 * Created by ingrid on 14/12/17.
 */
class PaymentCardDB {
    fun deleteCreditCard() {
        SQLite.delete().from(PaymentCard::class.java).execute()
    }

    fun saveCreditCard(card: PaymentCard) {
        card.save()
    }

    fun findCreditCard() : PaymentCard? {
        return SQLite.select().from(PaymentCard::class.java).querySingle()
    }
}