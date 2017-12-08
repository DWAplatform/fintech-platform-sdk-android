package com.dwaplatform.android.account.balance.db

import com.dwaplatform.android.account.balance.models.BalanceItem

/**
 * Created by ingrid on 07/12/17.
 */
interface BalancePersistence {

    fun getBalanceItem(accountId: String): BalanceItem?

    fun saveBalance(balance: BalanceItem)
}
