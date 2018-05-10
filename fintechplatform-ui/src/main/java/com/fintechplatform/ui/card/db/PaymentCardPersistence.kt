package com.fintechplatform.ui.card.db

import com.fintechplatform.api.card.models.PaymentCard

interface PaymentCardPersistence {

    open fun getPaymentCardItem(accountId: String): PaymentCard?

    fun savePaymentCard(paymentcard: PaymentCard)

    fun replace(paymentcard: PaymentCard)

    fun paymentCardId(accountId: String):String?

}