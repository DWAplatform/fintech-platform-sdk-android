package com.fintechplatform.api.card.helpers

import com.fintechplatform.api.card.models.PaymentCardItem

interface PaymentCardPersistence {

    open fun getPaymentCardItem(accountId: String): PaymentCardItem?

    fun savePaymentCard(paymentcard: PaymentCardItem)

    fun replace(paymentcard: PaymentCardItem)

    fun paymentCardId(accountId: String):String?

}