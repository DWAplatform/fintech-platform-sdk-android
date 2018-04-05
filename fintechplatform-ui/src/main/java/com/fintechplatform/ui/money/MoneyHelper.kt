package com.fintechplatform.ui.money

import com.fintechplatform.api.money.Money

class MoneyHelper {

    fun toString(money: Money): String {
        return money.toString() + " €"
    }

    fun toStringNoCurrency(money: Money): String {
        return money.toString()
    }

}