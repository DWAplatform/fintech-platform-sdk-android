package com.fintechplatform.api.payin.models

import java.util.*

data class PayInReply(val transactionid: String,
                       val securecodeneeded: Boolean,
                       val redirecturl: String?,
                       val status: PayInStatus,
                       val created: Date?,
                       val updated: Date? )

enum class PayInStatus {
    REQUEST, CREATED, SUCCEEDED, FAILED
}
