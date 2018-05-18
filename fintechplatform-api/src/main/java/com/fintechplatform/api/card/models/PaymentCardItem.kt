package com.fintechplatform.api.card.models

import java.util.*


enum class PaymentCardIssuer {
    VISA, MASTERCARD, DINERS, MAESTRO, UNKNOWN
}

enum class PaymentCardStatus {
    CREATED, VALIDATED, NOT_ACTIVE, INVALID
}

/**
 * PaymentCard data model
 */
data class PaymentCardItem(val id: String?,
                           val accountId: String,
                           val alias: String?,
                           val expiration: String?,
                           val currency: String,
                           val default: Boolean?,
                           val status: PaymentCardStatus?,
                           val issuer: PaymentCardIssuer?,
                           val created: Date?,
                           val updated: Date?)

