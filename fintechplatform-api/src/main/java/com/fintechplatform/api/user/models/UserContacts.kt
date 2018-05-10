package com.fintechplatform.api.user.models

data class UserContacts (val userId: String,
                         val tenantId: String,
                         val email: String?= null,
                         val telephone: String?= null)