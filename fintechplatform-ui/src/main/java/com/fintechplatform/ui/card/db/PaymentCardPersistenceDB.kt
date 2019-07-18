package com.fintechplatform.ui.card.db

import com.fintechplatform.api.card.models.PaymentCardItem


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
            // FIXME: add issuer, created, updated
            PaymentCardItem(cc.id, cc.accountId, cc.numberAlias, cc.expiration, "EUR",
                    cc.isDefault, cc.state, null, null, null)
        }
    }

    override fun savePaymentCard(paymentcard: PaymentCardItem) {
        // FIXME: save issuer, created, updated
        val card = PaymentCard()
        card.state = paymentcard.status
        card.id = paymentcard.id
        card.accountId = paymentcard.accountId
        card.expiration = paymentcard.expiration
        card.numberAlias = paymentcard.alias
        card.isDefault = paymentcard.default?: false

        paymentCardDB.savePaymentCard(card)
    }

    fun deletePaymentCard() {
        paymentCardDB.deletePaymentCard()
    }

    fun load(accountId: String): PaymentCardItem? {
        val optcc = paymentCardDB.findPaymentCard(accountId)
        return optcc?.let { cc ->
            // FIXME: add issuer, created, updated
            PaymentCardItem(cc.id, cc.accountId, cc.numberAlias, cc.expiration, "EUR",
                    cc.isDefault,  cc.state, null, null, null)
        }
    }

    fun findDefaultPaymentCard(): PaymentCardItem? {
        val optDefaultCard =  paymentCardDB.findDefaultCard()
        return optDefaultCard.let {
            // FIXME: add issuer, created, updated
            PaymentCardItem(it.id, it.accountId, it.numberAlias, it.expiration, "EUR",
                    it.isDefault, it.state, null, null, null)
        }
    }
}