package com.fintechplatform.api.profile.models

data class UserDocuments(val userId: String?,
                         val documentId: String?,
                         val docType: DocType?,
                         val bucketObjectIdPages: List<String>?)