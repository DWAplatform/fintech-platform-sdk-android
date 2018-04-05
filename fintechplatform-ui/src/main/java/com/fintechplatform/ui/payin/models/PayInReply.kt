package com.fintechplatform.ui.payin.models

data class PayInReply(val transactionid: String,
                      val securecodeneeded: Boolean,
                      val redirecturl: String?)
