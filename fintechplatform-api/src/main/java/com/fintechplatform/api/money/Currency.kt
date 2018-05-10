package com.fintechplatform.api.money

import android.annotation.TargetApi


enum class Currency {
    EUR,
    USD,
    GBP,
    SEK,
    NOK,
    DKK,
    CHF,
    PLN,
    CAD,
    AUD
//    @RequiresApi()
//    fun displayName(c: Currency) : String = java.util.Currency.getInstance(c.name).displayName
}