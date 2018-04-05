package com.fintechplatform.android.money

class MoneyHelper {

    fun toString(money: Money): String {
        return money.toString() + " €"
    }

    fun toStringNoCurrency(money: Money): String {
        return money.toString()
    }

}