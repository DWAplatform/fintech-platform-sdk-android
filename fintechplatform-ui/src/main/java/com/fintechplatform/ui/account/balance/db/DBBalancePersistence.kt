package com.fintechplatform.ui.account.balance.db

import com.fintechplatform.api.account.balance.models.BalanceItem
import com.fintechplatform.api.money.Money
import com.fintechplatform.ui.account.balance.helpers.BalancePersistence

class DBBalancePersistence constructor(val dbBalance: DBBalance): BalancePersistence {

    override fun getBalanceItem(accountId: String): BalanceItem? {
        val optobj = dbBalance.findBalance(accountId)

        if (optobj == null || optobj.amount == null) {
            return null
        }

        return BalanceItem(Money(optobj.amount, optobj.currency), Money(optobj.availableBalance, optobj.currency))

    }

    override fun saveBalance(balance: BalanceItem, accountId: String) {

        dbBalance.deleteBalance()
        val dbb = Balance()
        dbb.amount = balance.balance.value
        dbb.currency = balance.balance.currency
        dbb.accountId = accountId
        dbb.availableBalance = balance.availableBalance.value
        return dbBalance.saveBalance(dbb)

    }

}
