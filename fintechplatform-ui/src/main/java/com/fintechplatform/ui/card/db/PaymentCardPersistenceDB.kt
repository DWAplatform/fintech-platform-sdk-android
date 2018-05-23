package com.fintechplatform.ui.card.db

import com.fintechplatform.api.account.models.Account
import com.fintechplatform.api.card.models.PaymentCard
import com.fintechplatform.api.card.models.PaymentCardStatus

import com.fintechplatform.api.user.models.User
import java.util.*


class PaymentCardPersistenceDB constructor(val paymentCardDB: PaymentCardDB): PaymentCardPersistence {

    override fun paymentCardId(accountId: String) : String? {
        return paymentCardDB.findPaymentCard(accountId)?.id
    }

    override fun replace(paymentcard: PaymentCard) {
        paymentCardDB.deletePaymentCard()
        savePaymentCard(paymentcard)
    }

    override fun getPaymentCardItem(accountId: String): PaymentCard? {
        val optcc = paymentCardDB.findPaymentCard(accountId)
        return optcc?.let { cc ->
            // FIXME: add issuer, created, updated
            PaymentCard(cc.id, cc.accountId, cc.numberAlias, cc.expiration, "EUR",
                    cc.isDefault, cc.state, null, null, null)
        }
    }

    override fun savePaymentCard(paymentcard: PaymentCard) {
        // FIXME: save issuer, created, updated
        val card = PaymentCard()
        card.id = paymentcard.cardId
        card.state = paymentcard.status.toString()
        card.accountId = paymentcard.accountId
        card.expiration = paymentcard.expiration
        card.numberAlias = paymentcard.alias
        card.isDefault = paymentcard.default?: false

        paymentCardDB.savePaymentCard(card)
    }

    fun deletePaymentCard() {
        paymentCardDB.deletePaymentCard()
    }

    fun load(accountId: String): PaymentCard? {
        val optcc = paymentCardDB.findPaymentCard(accountId)
        return optcc?.let { cc ->
            // FIXME: add issuer, created, updated
            PaymentCard(cc.id, cc.accountId, cc.numberAlias, cc.expiration, "EUR",
                    cc.isDefault,  cc.state, null, null, null)
        }
    }

    fun findDefaultPaymentCard(): PaymentCard? {
        val optDefaultCard =  paymentCardDB.findDefaultCard()
        return optDefaultCard.let {
            // FIXME: add issuer, created, updated
            PaymentCard(it.id, it.accountId, it.numberAlias, it.expiration, "EUR",
                    it.isDefault, it.state, null, null, null)
        }
    }
}