package com.dwaplatform.android.account.balance.helpers

import com.dwaplatform.android.account.balance.api.BalanceAPI
import com.dwaplatform.android.account.balance.models.BalanceItem
import com.dwaplatform.android.money.Money
import javax.inject.Inject

/**
 * Created by tcappellari on 08/12/2017.
 */
open class BalanceHelper @Inject constructor(val persistence: BalancePersistence,
                                             val api: BalanceAPI) {


    fun getAndUpdateCachedBalance(token: String, userId: String, accountId: String, callback: (Money?, Exception?) -> Unit): Money {

        api.balance(token, userId, accountId) {  optbalance, opterror ->
            if (opterror != null) {
                callback(null, opterror)
                return@balance
            }

            if (optbalance == null) {
                callback(null, Exception("Balance null"))
                return@balance
            }

            val bal = optbalance
            val money = Money(bal)
            persistence.saveBalance(BalanceItem(accountId, money))
            callback(money, null)
        }

        return persistence.getBalanceItem(accountId)?.money ?: Money(0)


    }




}