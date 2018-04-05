package com.fintechplatform.api.profile.models

data class UserContacts (val userid: String,
                         val tenantId: String,
                         val email: String?= null,
                         val telephone: String?= null)