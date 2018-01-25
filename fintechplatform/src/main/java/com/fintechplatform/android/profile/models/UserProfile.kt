package com.fintechplatform.android.profile.models

data class UserProfile(val userid: String,
                       val name: String? = null,
                       val surname: String? = null,
                       val nationality: String? = null,
                       val dateOfBirth: String? = null,
                       val address: String? = null,
                       val ZIPcode: String? = null,
                       val city: String? = null,
                       val telephone: String? = null,
                       val email: String? = null,
                       val photo: String? = null,
                       val countryofresidence: String? = null,
                       val jobinfo: String? = null,
                       val income: String? = null,
                       val tokenuser: String? = null)