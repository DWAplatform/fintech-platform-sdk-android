package com.fintechplatform.ui.money

open class FeeHelper {

    open fun calcPayInFee(amount: Long): Long {
        return Math.round(18 + (amount * 1.8) / 100)
    }

    fun calcPayOutFee(amount: Long): Long {
        return 0
    }

    fun calcPaymentFee(amount: Long): Long {
        return 0
    }
}