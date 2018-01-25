package com.fintechplatform.android.profile.models

data class UserDocuments(val userId: String?=null,
                         val doctype: String?= null,
                         val pages: Array<String?>?= null)