package com.fintechplatform.api.transfer.models

import com.fintechplatform.api.money.Money


data class TransferAccountModel(val userid: String,
                                val accountId: String,
                                val tenantId: String,
                                val accountType: String,
                                val name: String?=null,
                                val photo: String?=null,
                                val type: String?=null,
                                val created: String?=null,
                                val transferId: String?=null,
                                val money: Money?=null,
                                val fee: Money?=null,
                                val message: String?=null)
