package com.fintechplatform.android.card.db

import com.fintechplatform.android.card.helpers.PaymentCardPersistence
import com.fintechplatform.android.card.models.PaymentCardItem

class PaymentCardPersistenceDB constructor(val paymentCardDB: PaymentCardDB): PaymentCardPersistence {

    override fun paymentCardId(accountId: String) : String? {
        return paymentCardDB.findPaymentCard(accountId)?.id
    }

    override fun replace(paymentcard: PaymentCardItem) {
        paymentCardDB.deletePaymentCard()
        savePaymentCard(paymentcard)
    }

    override fun getPaymentCardItem(accountId: String): PaymentCardItem? {
        val optcc = paymentCardDB.findPaymentCard(accountId)
        return optcc?.let { cc ->
            PaymentCardItem(cc.id, cc.accountId, cc.numberAlias, cc.expiration, "EUR", null, cc.state, "token", null)
        }
    }

    override fun savePaymentCard(paymentcard: PaymentCardItem) {
        val card = PaymentCard()
        card.state = paymentcard.status
        card.id = paymentcard.id
        card.accountId = paymentcard.accountId
        card.expiration = paymentcard.expiration
        card.numberAlias = paymentcard.alias

        paymentCardDB.savePaymentCard(card)
    }

    fun deletePaymentCard() {
        paymentCardDB.deletePaymentCard()
    }

    fun load(accountId: String): PaymentCardItem? {
        val optcc = paymentCardDB.findPaymentCard(accountId)
        return optcc?.let { cc ->
            PaymentCardItem(cc.id, cc.accountId, cc.numberAlias, cc.expiration, "EUR", null,  cc.state, "token", null)
        }
    }
}