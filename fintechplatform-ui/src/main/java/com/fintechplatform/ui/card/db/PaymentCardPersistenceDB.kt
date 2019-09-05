package com.fintechplatform.ui.card.db

import android.util.Log
import com.fintechplatform.api.card.models.PaymentCardItem
import com.fintechplatform.api.card.models.PaymentCardStatus


class PaymentCardPersistenceDB constructor(val paymentCardDB: PaymentCardDB): PaymentCardPersistence {

    override fun paymentCardId(accountId: String) : String? {
        Log.d("PaymentCard", paymentCardDB.findPaymentCard(accountId).toString())
        return paymentCardDB.findPaymentCard(accountId)?.id
    }

    override fun replace(paymentcard: PaymentCardItem) {
        paymentCardDB.deletePaymentCard()
        savePaymentCard(paymentcard)
    }

    override fun getPaymentCardItem(accountId: String): PaymentCardItem? {
        val optcc = paymentCardDB.findPaymentCard(accountId)
        return optcc?.let { cc ->
            cc.accountId?.let { accountId ->
                cc.state?.let { state ->
                    // FIXME: add issuer, created, updated
                    PaymentCardItem(cc.id, accountId, cc.numberAlias, cc.expiration, "EUR",
                            cc.isDefault, PaymentCardStatus.valueOf(state), null, null, null)
                }
            }
        }
    }

    override fun savePaymentCard(paymentcard: PaymentCardItem) {
        // FIXME: save issuer, created, updated
        val card = PaymentCard()
        card.state = paymentcard.status.toString()
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
            cc.accountId?.let { accountId ->
                cc.state?.let { state ->
                    // FIXME: add issuer, created, updated
                    PaymentCardItem(cc.id, accountId, cc.numberAlias, cc.expiration, "EUR",
                            cc.isDefault,  PaymentCardStatus.valueOf(state), null, null, null)
                }
            }
        }
    }

    fun findDefaultPaymentCard(): PaymentCardItem? {
        val optDefaultCard =  paymentCardDB.findDefaultCard()
        return optDefaultCard?.let {
            it.accountId?.let { accountId ->
                it.state?.let { state ->
                    // FIXME: add issuer, created, updated
                    PaymentCardItem(it.id, accountId, it.numberAlias, it.expiration, "EUR",
                            it.isDefault, PaymentCardStatus.valueOf(state), null, null, null)
                }
            }
        }
    }
}