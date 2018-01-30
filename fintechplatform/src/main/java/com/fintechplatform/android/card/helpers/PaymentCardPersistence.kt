package com.fintechplatform.android.card.helpers

import com.fintechplatform.android.card.models.PaymentCardItem
interface PaymentCardPersistence {

    open fun getPaymentCardItem(accountId: String): PaymentCardItem?

    fun savePaymentCard(paymentcard: PaymentCardItem)

    fun replace(paymentcard: PaymentCardItem)

    fun paymentCardId():String?

}