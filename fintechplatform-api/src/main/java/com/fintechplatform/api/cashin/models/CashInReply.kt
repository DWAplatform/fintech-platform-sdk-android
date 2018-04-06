package com.fintechplatform.api.cashin.models

data class CashInReply(val transactionid: String,
                       val securecodeneeded: Boolean,
                       val redirecturl: String?)
