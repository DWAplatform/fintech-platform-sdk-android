package com.fintechplatform.ui.card.db

import com.raizlabs.android.dbflow.sql.language.SQLite


class PaymentCardDB {

    fun deletePaymentCard() {
        SQLite.delete().from(PaymentCard::class.java).execute()
    }

    fun savePaymentCard(card: PaymentCard) {
        card.save()
    }

    // TODO handle primary / secondary accountId
    fun findPaymentCard(accountId: String): PaymentCard? {
        return SQLite.select()
                .from(PaymentCard::class.java)
                //                .where(PaymentCard_Table.accountId.eq(accountId))
                .querySingle()
    }

    fun findDefaultCard(): PaymentCard? {
        return SQLite.select()
                .from(PaymentCard::class.java)
                .where(PaymentCard_Table.isDefault.eq(true))
                .querySingle()
    }
}



/*
val results = (select
              from Result::class
              where (column eq 6)
              and (column2 `in`("5", "6", "9"))
              groupBy column).list
              // can call .result for single result
              // .hasData if it has results
              // .statement for a compiled statement
 */
