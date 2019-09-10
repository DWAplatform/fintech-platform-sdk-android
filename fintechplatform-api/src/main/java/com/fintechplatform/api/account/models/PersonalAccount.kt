package com.fintechplatform.api.account.models


data class PersonalAccount(val ownerId: String,
                           val accountId: String,
                           val accountType: AccountType,
                           val tenantId: String,
                           val levelStatus: AccountLevelStatus,
                           val level: AccountLevel) {

}