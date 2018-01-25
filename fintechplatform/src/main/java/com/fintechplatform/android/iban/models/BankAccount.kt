package com.fintechplatform.android.iban.models

data class BankAccount(val bankaccountid: String,
                       val iban: String?= null,
                       val activestate: String)