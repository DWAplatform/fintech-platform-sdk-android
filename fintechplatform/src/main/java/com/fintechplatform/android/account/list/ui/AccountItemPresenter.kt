package com.fintechplatform.android.account.list.ui

import com.fintechplatform.android.account.list.models.AccountItem

class AccountItemPresenter constructor(val view: AccountsItemContract.View): AccountsItemContract.Presenter {

    override fun item(item: AccountItem) {
        view.setAccountNameText(item.aspName)
        view.setAccountIcon()
    }

    override fun userClick(item: AccountItem, onClick: (AccountItem) -> Unit) {

    }
}