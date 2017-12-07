package com.dwaplatform.android.account.balance

import com.dwafintech.dwapay.model.Money
import com.dwaplatform.android.account.Account

/**
 * Created by ingrid on 07/12/17.
 */
data class BalanceItem(val accountId: String, val money: Money)