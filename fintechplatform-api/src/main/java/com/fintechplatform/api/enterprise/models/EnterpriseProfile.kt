package com.fintechplatform.api.enterprise.models

data class EnterpriseProfile (val enterpriseId: String,
                              val legalRapresentativeId: String?=null,
                              val name: String?=null,
                              val telephone: String?=null,
                              val email: String?=null,
                              val enterpriseType: String?=null,
                              val countryHeadquarters: String?=null,
                              val cityOfHeadquarters: String?=null,
                              val addressOfHeadquarters: String?=null,
                              val postalCodeHeadquarters: String?=null)