package com.fintechplatform.api.card.models

import com.fintechplatform.api.money.Currency
import java.util.*


/**
 * PaymentCard data model
 */
data class PaymentCard(val cardId: String?,
                       val alias: String?,
                       val expiration: String?,
                       val currency: Currency?,
                       val issuer: PaymentCardIssuer?,
                       val status: PaymentCardStatus?,
                       val default: Boolean?,
                       val created: Date?,
                       val updated: Date?)
