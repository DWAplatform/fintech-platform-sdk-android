package com.fintechplatform.android.payin.models

/**
 * Created by ingrid on 06/12/17.
 */

data class PayInReply(val transactionid: String,
                      val securecodeneeded: Boolean,
                      val redirecturl: String?)
