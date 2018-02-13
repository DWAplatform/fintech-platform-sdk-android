package com.fintechplatform.android.account.balance.db

import com.fintechplatform.android.account.balance.helpers.BalancePersistence
import com.fintechplatform.android.account.balance.models.BalanceItem
import com.fintechplatform.android.money.Money

class DBBalancePersistence constructor(val dbBalance: DBBalance): BalancePersistence {

    override fun getBalanceItem(accountId: String): BalanceItem? {
        val optobj = dbBalance.findBalance(accountId)

        if (optobj == null || optobj.amount == null) {
            return null
        }

        return BalanceItem(optobj.accountId, Money(optobj.amount, optobj.currency))

    }

    override fun saveBalance(balance: BalanceItem) {

        dbBalance.deleteBalance()
        val dbb = Balance()
        dbb.amount = balance.money.value
        dbb.currency = balance.money.currency
        dbb.accountId = balance.accountId
        return dbBalance.saveBalance(dbb)

    }

}
