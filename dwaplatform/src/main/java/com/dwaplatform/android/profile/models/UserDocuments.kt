package com.dwaplatform.android.profile.models

data class UserDocuments(val userId: String,
                         val doctype: String?= null,
                         val pages: Array<String?>?= null)