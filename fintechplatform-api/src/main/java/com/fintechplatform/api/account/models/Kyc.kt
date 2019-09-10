package com.fintechplatform.api.account.models

import com.fintechplatform.api.net.Error
import java.util.*

data class Kyc(val kycId: UUID,
               val documentId: UUID,
               val status: KycStatus,
               val created: Date?,
               val error: Error?=null)