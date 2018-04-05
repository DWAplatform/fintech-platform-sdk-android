package com.fintechplatform.android.account.balance.helpers

import com.fintechplatform.android.account.balance.models.BalanceItem

open interface BalancePersistence {

    open fun getBalanceItem(accountId: String): BalanceItem?

    fun saveBalance(balance: BalanceItem)
}
