package com.fintechplatform.android.enterprise.models

data class EnterpriseProfile (val accountId: String,
                              val name: String?=null,
                              val telephone: String?=null,
                              val email: String?=null,
                              val enterpriseType: String?=null,
                              val countryHeadquarters: String?=null,
                              val cityOfHeadquarters: String?=null,
                              val addressOfHeadquarters: String?=null,
                              val postalCodeHeadquarters: String?=null)
enum class Type {
    BUSINESS,
    ORGANIZATION,
    SOLETRADER
}