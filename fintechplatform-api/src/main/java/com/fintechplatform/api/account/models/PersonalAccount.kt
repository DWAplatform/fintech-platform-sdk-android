package com.fintechplatform.api.account.models


data class PersonalAccount(val account: Account,
                           val levelStatus: AccountLevelStatus,
                           val level: AccountLevel) {

}