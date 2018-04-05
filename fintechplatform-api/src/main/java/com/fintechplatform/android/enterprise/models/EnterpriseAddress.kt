package com.fintechplatform.android.enterprise.models

data class EnterpriseAddress (val ownerId: String,
                              val accountId: String,
                              val tenantId: String,
                              val address: String?=null,
                              val city: String?= null,
                              val postalCode: String?= null,
                              val country: String?= null)
