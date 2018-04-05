package com.fintechplatform.api.iban.models

data class BankAccount(val bankaccountid: String?=null,
                       val accountId: String,
                       val iban: String?= null,
                       val activestate: String?=null)

/*
case class AccountLinkedBank(tenantId: UUID,
                             ownerId: UUID,
                             accountId: UUID,
                             bankId: UUID,
                             iban: String,
                             bic: Option[String],
                             status: Option[String],
                             created: Option[String] = None,
                             updated: Option[String] = None)
 */