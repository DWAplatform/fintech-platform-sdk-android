package com.fintechplatform.ui.account.balance.models

import com.fintechplatform.api.money.Money

data class BalanceItem(val accountId: String, val money: Money)