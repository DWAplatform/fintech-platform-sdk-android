package com.fintechplatform.android.enterprise.models

data class EnterpriseAddress (val accountId: String,
                              val address: String?=null,
                              val city: String?= null,
                              val postalCode: String?= null,
                              val country: String?= null)
