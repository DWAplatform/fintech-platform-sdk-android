package com.fintechplatform.ui.account.balance.helpers

import com.fintechplatform.api.account.balance.models.BalanceItem

interface BalancePersistence {

    fun getBalanceItem(accountId: String): BalanceItem?

    fun saveBalance(balance: BalanceItem, accountId: String)
}
