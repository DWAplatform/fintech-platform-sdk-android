package com.dwaplatform.android.profile.models

data class UserLightData(val userid: String,
                         val name: String? = null,
                         val surname: String?= null,
                         val birthday: String?= null,
                         val nationality: String?= null)