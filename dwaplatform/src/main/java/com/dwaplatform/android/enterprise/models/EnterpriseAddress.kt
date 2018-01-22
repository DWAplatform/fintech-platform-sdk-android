package com.dwaplatform.android.enterprise.models

data class EnterpriseAddress (val userid: String,
                              val address: String?=null,
                              val city: String?= null,
                              val postalCode: String?= null,
                              val country: String?= null)
