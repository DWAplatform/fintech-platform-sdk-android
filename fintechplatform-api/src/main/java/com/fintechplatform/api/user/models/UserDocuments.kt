package com.fintechplatform.api.user.models

data class UserDocuments(val docId: String?=null,
                         val doctype: String?= null,
                         val pages: List<String?>?= null)