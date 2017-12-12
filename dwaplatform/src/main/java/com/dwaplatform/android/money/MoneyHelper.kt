package com.dwaplatform.android.money

import com.dwaplatform.android.models.Money

/**
 * Created by ingrid on 06/12/17.
 */
class MoneyHelper {

    fun toString(money: Money): String {
        return money.toString() + " €"
    }

    fun toStringNoCurrency(money: Money): String {
        return money.toString()
    }

}