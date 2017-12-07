package com.dwaplatform.android.account.balance.db

import com.dwafintech.dwapay.model.Money
import com.dwaplatform.android.account.balance.BalanceHelper
import com.dwaplatform.android.account.balance.BalanceItem

/**
 * Created by ingrid on 07/12/17.
 */
class DBBalanceHelper constructor(val dbBalance: DBBalance): BalanceHelper {

    override fun getBalanceItem(accountId: String): BalanceItem? {
        /*val optobj = dbBalance.findBalance(accountId)

        if (optobj == null || optobj.amount == null) {
            return null
        }

        return BalanceItem(optobj.accountId, Money(optobj.amount, optobj.currency))
        */
        return null
    }

    override fun saveBalance(balance: BalanceItem) {
        /*
        dbBalance.deleteBalance()
        val dbb = Balance()
        dbb.amount = balance.money.value
        dbb.currency = balance.money.currency
        dbb.accountId = balance.accountId
        return dbBalance.saveBalance(dbb)
        */
    }



}
