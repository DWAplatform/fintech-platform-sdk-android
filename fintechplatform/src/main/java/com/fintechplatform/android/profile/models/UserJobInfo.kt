package com.fintechplatform.android.profile.models

data class UserJobInfo (val userid: String,
                        val tenantId: String,
                        val jobinfo: String?= null,
                        val income: String?= null)