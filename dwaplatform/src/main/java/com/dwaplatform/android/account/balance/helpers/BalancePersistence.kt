package com.dwaplatform.android.account.balance.helpers

import com.dwaplatform.android.account.balance.models.BalanceItem

/**
 * Created by ingrid on 07/12/17.
 */
open interface BalancePersistence {

    open fun getBalanceItem(accountId: String): BalanceItem?

    fun saveBalance(balance: BalanceItem)
}
