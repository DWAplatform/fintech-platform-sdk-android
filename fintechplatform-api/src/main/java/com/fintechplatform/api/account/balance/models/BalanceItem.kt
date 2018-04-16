package com.fintechplatform.api.account.balance.models

import com.fintechplatform.api.money.Money


public class BalanceItem(val balance: Money,
                         val availableBalance: Money) {
}