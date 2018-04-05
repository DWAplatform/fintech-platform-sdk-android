package com.fintechplatform.api.payin.models

data class PayInReply(val transactionid: String,
                      val securecodeneeded: Boolean,
                      val redirecturl: String?)
