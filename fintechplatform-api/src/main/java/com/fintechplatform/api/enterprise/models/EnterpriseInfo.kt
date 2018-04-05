package com.fintechplatform.api.enterprise.models

data class EnterpriseInfo (val ownerId: String,
                           val accountId: String,
                           val tenantId: String,
                           val name: String?=null,
                           val enterpriseType: String?= null)