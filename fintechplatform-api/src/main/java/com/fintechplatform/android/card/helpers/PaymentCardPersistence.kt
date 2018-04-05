package com.fintechplatform.android.card.helpers

interface PaymentCardPersistence {

    open fun getPaymentCardItem(accountId: String): PaymentCardItem?

    fun savePaymentCard(paymentcard: PaymentCardItem)

    fun replace(paymentcard: PaymentCardItem)

    fun paymentCardId(accountId: String):String?

}