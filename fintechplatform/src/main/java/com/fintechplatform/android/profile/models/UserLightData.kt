package com.fintechplatform.android.profile.models

data class UserLightData(val userid: String,
                         val tenantId: String,
                         val name: String? = null,
                         val surname: String?= null,
                         val birthday: String?= null,
                         val nationality: String?= null)