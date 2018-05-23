package com.fintechplatform.ui.account.balance.helpers

import com.fintechplatform.api.account.balance.api.BalanceAPI
import com.fintechplatform.api.account.balance.models.BalanceItem
import com.fintechplatform.api.account.models.AccountType
import com.fintechplatform.api.money.Money
import javax.inject.Inject

open class BalanceHelper @Inject constructor(val persistence: BalancePersistence,
                                             val api: BalanceAPI) {


    fun getAndUpdateCachedBalance(token: String, userId: String, accountId: String, accountType: AccountType, tenantId: String, callback: (BalanceItem?, Exception?) -> Unit): Money {

        api.balance(token, userId, accountId, accountType, tenantId) {  optbalance, opterror ->
            if (opterror != null) {
                callback(null, opterror)
                return@balance
            }

            if (optbalance == null) {
                callback(null, Exception("Balance null"))
                return@balance
            }

            val bal = optbalance
            persistence.saveBalance(BalanceItem(bal.balance, bal.availableBalance), accountId)
            callback(BalanceItem(bal.balance, bal.availableBalance), null)
        }

        return persistence.getBalanceItem(accountId)?.balance ?: Money(0)
    }
}