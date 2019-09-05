package com.fintechplatform.ui.account.balance.db

import com.fintechplatform.api.account.balance.models.BalanceItem
import com.fintechplatform.api.money.Money
import com.fintechplatform.ui.account.balance.helpers.BalancePersistence

class DBBalancePersistence constructor(val dbBalance: DBBalance): BalancePersistence {

    override fun getBalanceItem(accountId: String): BalanceItem? {
        val optobj = dbBalance.findBalance(accountId)
        optobj?.amount?.let { amount ->
            optobj.availableBalance?.let{ availableBalance ->
                return BalanceItem(Money(amount, optobj.currency), Money(availableBalance, optobj.currency))
            }
        }
        return null
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
