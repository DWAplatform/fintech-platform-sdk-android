package com.fintechplatform.api.account.models

//import com.fintechplatform.api.enterprise.models.Enterprise
//import com.fintechplatform.api.user.models.User
import java.util.*


data class Account internal constructor (val tenantId: UUID,
                                         val accountType: AccountType,
                                         val ownerId: UUID,
                                         val accountId: UUID) {

    companion object {
//
//        fun personalAccount(user: User,
//                            accountId: UUID): Account = Account(user.tenantId, AccountType.PERSONAL, user.userId, accountId)
//
//        fun businessAccount(enterprise: Enterprise,
//                            accountId: UUID): Account = Account(enterprise.tenantId, AccountType.BUSINESS, enterprise.enterpriseId, accountId)
    }
}





