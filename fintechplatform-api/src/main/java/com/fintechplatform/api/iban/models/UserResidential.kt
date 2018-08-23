package com.fintechplatform.api.iban.models

data class UserResidential(val userId: String,
                           val tenantId: String,
                           val address: String?=null,
                           val postalCode: String?=null,
                           val city: String?=null,
                           val countryOfResidence: String?=null)