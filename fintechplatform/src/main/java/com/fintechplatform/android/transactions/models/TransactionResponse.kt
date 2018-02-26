package com.fintechplatform.android.transactions.models

data class TransactionResponse (
                            val transactionids: String,
                            val status: String,
                            val operationtype: String,
                            val creationdate: String,
                            val crediteduserid: String? = null,
                            val debiteduserid: String? = null,
                            val creditedfunds: Long? = null,
                            val debitedfunds: Long? = null,
                            val message: String? = null,
                            val error: String?=null)