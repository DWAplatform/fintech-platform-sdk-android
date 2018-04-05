package com.fintechplatform.android.account.balance.models

import com.fintechplatform.android.money.Money

data class BalanceItem(val accountId: String, val money: Money)