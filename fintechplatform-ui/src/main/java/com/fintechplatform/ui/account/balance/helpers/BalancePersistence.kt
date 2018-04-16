package com.fintechplatform.ui.account.balance.helpers

import com.fintechplatform.api.account.balance.models.BalanceItem

open interface BalancePersistence {

    open fun getBalanceItem(accountId: String): BalanceItem?

    fun saveBalance(balance: BalanceItem, accountId: String)
}
