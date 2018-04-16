package com.fintechplatform.ui.account.balance.helpers

import com.fintechplatform.ui.account.balance.models.BalanceItem
import javax.inject.Inject

open class BalanceHelper @Inject constructor(val persistence: BalancePersistence,
                                             val api: BalanceAPI) {


    fun getAndUpdateCachedBalance(token: String, userId: String, accountId: String, accountType: String, tenantId: String, callback: (Money?, Exception?) -> Unit): Money {

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
            persistence.saveBalance(BalanceItem(accountId, bal[0]))
            callback(bal[0], null)
        }

        return persistence.getBalanceItem(accountId)?.money ?: Money(0)


    }




}