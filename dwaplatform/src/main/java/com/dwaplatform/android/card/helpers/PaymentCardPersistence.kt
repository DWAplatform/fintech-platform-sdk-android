package com.dwaplatform.android.card.helpers

import com.dwaplatform.android.card.models.PaymentCardItem

/**
 * Created by ingrid on 14/12/17.
 */
interface PaymentCardPersistence {

    open fun getPaymentCardItem(accountId: String): PaymentCardItem?

    fun savePaymentCard(paymentcard: PaymentCardItem)

    fun replace(paymentcard: PaymentCardItem)

    fun paymentCardId():String?

}