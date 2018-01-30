package com.fintechplatform.android.card.db

import com.raizlabs.android.dbflow.sql.language.SQLite

class PaymentCardDB {
    fun deletePaymentCard() {
        SQLite.delete().from(PaymentCard::class.java).execute()
    }

    fun savePaymentCard(card: PaymentCard) {
        card.save()
    }

    fun findPaymentCard() : PaymentCard? {
        return SQLite.select().from(PaymentCard::class.java).querySingle()
    }
}