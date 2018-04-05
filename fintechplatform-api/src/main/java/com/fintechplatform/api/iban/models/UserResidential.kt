package com.fintechplatform.api.iban.models

data class UserResidential(val userid: String,
                           val tenantid: String,
                           val address: String?,
                           val ZIPcode: String?,
                           val city: String?,
                           val countryofresidence: String?)