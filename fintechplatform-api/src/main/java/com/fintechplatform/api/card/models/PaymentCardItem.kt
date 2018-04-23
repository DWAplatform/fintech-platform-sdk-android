package com.fintechplatform.api.card.models

/**
 * PaymentCard data model
 */
data class PaymentCardItem(val id: String?,
                           val accountId: String,
                           val alias: String?,
                           val expiration: String?,
                           val currency: String,
                           val default: Boolean?,
                           val status: String?,
                           val create: String?="")

