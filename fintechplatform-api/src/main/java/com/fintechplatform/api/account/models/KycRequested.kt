package com.fintechplatform.api.account.models

import java.util.*


data class KycRequested(val kycId: UUID,
                        val documentId: UUID,
                        val status: KycStatus)