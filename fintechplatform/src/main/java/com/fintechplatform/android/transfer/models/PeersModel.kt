package com.fintechplatform.android.transfer.models


data class PeersModel(val userid: String,
                      val accountId: String,
                      val tenantId: String,
                      val name: String?=null,
                      val surname: String?=null,
                      val photo: String?=null,
                      val type: String?=null)