package com.fintechplatform.api.cashin.models

import java.util.*

data class CashInReply(val transactionid: String,
                       val securecodeneeded: Boolean,
                       val redirecturl: String?,
                       val status: CashInStatus,
                       val created: Date?,
                       val updated: Date? )

enum class CashInStatus {
    REQUEST, CREATED, SUCCEEDED, FAILED
}
