package com.dwaplatform.android.account.balance

import com.dwaplatform.android.account.Account
import com.dwaplatform.android.models.Amount

/**
 * Created by ingrid on 06/12/17.
 */
class Balance constructor(var account: Account) {
    fun getBalance(): Amount {
        return Amount()
    }
}