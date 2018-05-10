package com.fintechplatform.api.cashin.models

import com.fintechplatform.api.money.Money
import java.util.*

data class CashInResponse(val transactionId: UUID,
                          val amount: Money,
                          val fee: Money,
                          val securecodeneeded: Boolean,
                          val redirecturl: String?,
                          val status: CashInStatus?,
                          val created: Date?,
                          val updated: Date? )
