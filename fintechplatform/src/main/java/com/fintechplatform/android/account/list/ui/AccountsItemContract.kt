package com.fintechplatform.android.account.list.ui

import com.fintechplatform.android.account.list.models.AccountItem

interface AccountsItemContract {
    interface View {
        fun setAccountNameText(account: String)
        fun setAccountIcon()
    }

    interface Presenter {
        fun item(item: AccountItem)
        fun userClick(item: AccountItem, onClick: (AccountItem) -> Unit)
    }
}