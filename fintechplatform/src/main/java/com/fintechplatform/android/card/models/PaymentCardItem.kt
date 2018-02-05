package com.fintechplatform.android.card.models

import java.util.*

/**
 * PaymentCard data model
 */
data class PaymentCardItem(val id: String?,
                       val alias: String?,
                       val expiration: String?,
                       val currency: String,
                       val default: Boolean?,
                       val status: String?,
                       val token: String?,
                       val create: String?)

