package com.dwaplatform.android.card.db

import com.dwaplatform.android.card.helpers.PaymentCardPersistence
import com.dwaplatform.android.card.models.PaymentCardItem

/**
 * Created by ingrid on 14/12/17.
 */

class PaymentCardPersistenceDB constructor(val paymentCardDB: PaymentCardDB): PaymentCardPersistence {

    override fun paymentCardId() : String? {
        return paymentCardDB.findPaymentCard()?.id
    }

    override fun replace(paymentcard: PaymentCardItem) {
        paymentCardDB.deletePaymentCard()
        savePaymentCard(paymentcard)
    }

    override fun getPaymentCardItem(accountId: String): PaymentCardItem? {
        val optcc = paymentCardDB.findPaymentCard()
        return optcc?.let { cc ->
            PaymentCardItem(cc.id, cc.numberAlias, cc.expiration, "EUR", null, cc.state, "token", null)
        }
    }

    override fun savePaymentCard(paymentcard: PaymentCardItem) {
        val card = PaymentCard()
        card.state = paymentcard.status
        card.id = paymentcard.id
        card.expiration = paymentcard.expiration
        card.numberAlias = paymentcard.alias

        paymentCardDB.savePaymentCard(card)
    }
}