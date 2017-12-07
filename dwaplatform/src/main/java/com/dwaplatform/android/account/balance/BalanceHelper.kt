package com.dwaplatform.android.account.balance

import com.dwafintech.dwapay.model.Money
import com.dwaplatform.android.account.balance.db.Balance

/**
 * Created by ingrid on 07/12/17.
 */
interface BalanceHelper {

    fun getBalanceItem(accountId: String): BalanceItem?

    fun saveBalance(balance: BalanceItem)
}