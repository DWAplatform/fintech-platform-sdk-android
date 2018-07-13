package com.fintechplatform.api.account.models

import java.util.*


data class KycRequested(val kcyId: UUID,
                        val documentId: UUID,
                        val status: KycStatus)