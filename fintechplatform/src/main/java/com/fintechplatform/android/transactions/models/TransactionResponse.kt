package com.fintechplatform.android.transactions.models

data class TransactionResponse (val transactionIds: String,
                                val status: String,
                                val operationType: String,
                                val creationDate: String,
                                val creditedUserid: String? = null,
                                val debitedUserid: String? = null,
                                val creditedFunds: Long? = null,
                                val debitedFunds: Long? = null,
                                val creditedName: String?=null,
                                val debitedName: String?=null,
                                val creditedPhoto: String?=null,
                                val debitedPhoto: String?=null,
                                val message: String? = null,
                                val error: String?=null)