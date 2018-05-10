package com.fintechplatform.api.user.models

data class UserLightData(val userid: String,
                         val tenantId: String,
                         val name: String? = null,
                         val surname: String?= null,
                         val birthday: String?= null,
                         val nationality: String?= null)