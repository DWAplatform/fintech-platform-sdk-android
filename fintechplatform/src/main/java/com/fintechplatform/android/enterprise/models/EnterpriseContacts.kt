package com.fintechplatform.android.enterprise.models

data class EnterpriseContacts(val ownerId: String,
                               val accountId: String,
                               val tenantId: String,
                               val email: String?= null,
                               val telephone: String?= null)