package com.fintechplatform.ui.card.db

import com.fintechplatform.api.card.models.PaymentCardItem

interface PaymentCardPersistence {

    fun getPaymentCardItem(accountId: String): PaymentCardItem?

    fun savePaymentCard(paymentcard: PaymentCardItem)

    fun replace(paymentcard: PaymentCardItem)

    fun paymentCardId(accountId: String):String?

}