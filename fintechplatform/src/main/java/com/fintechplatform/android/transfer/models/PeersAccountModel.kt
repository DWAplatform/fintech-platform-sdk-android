package com.fintechplatform.android.transfer.models


data class PeersAccountModel(val userid: String,
                             val accountId: String,
                             val tenantId: String,
                             val accountType: String,
                             val name: String?=null,
                             val surname: String?=null,
                             val photo: String?=null,
                             val type: String?=null,
                             val created: String?=null)