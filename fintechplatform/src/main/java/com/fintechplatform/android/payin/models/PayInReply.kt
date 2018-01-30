package com.fintechplatform.android.payin.models

data class PayInReply(val transactionid: String,
                      val securecodeneeded: Boolean,
                      val redirecturl: String?)
