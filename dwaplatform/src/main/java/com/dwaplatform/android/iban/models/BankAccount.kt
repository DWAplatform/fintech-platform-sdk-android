package com.dwaplatform.android.iban.models

data class BankAccount(val bankaccountid: String,
                       val iban: String?= null,
                       val activestate: String)