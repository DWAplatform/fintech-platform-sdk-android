package com.fintechplatform.android.account.list.models

class AccountsManager {
    private val items = mutableListOf<AccountItem>()

    open fun count(): Int { return items.size }

//    open fun initAll(items: List<AccountItem>): Boolean {
//        val lastorder = if (this.items.isEmpty())
//            Long.MAX_VALUE else this.items[0].order
//
//        this.items.clear()
//
//        items.forEach { item ->
//            val newitem =
//                    if (item.order > lastorder)
//                        AccountItem(
//                                item.accounId,
//                                item.level,
//                                item.aspName,
//                                item.accountType)
//                    else item
//            this.items.add(newitem)
//        }
//
//        return true
//    }

    fun add(item: AccountItem) {
        items.add(item)
    }

    fun set(item: AccountItem, index: Int) {
        items[index] = item
    }

    open fun item(index: Int): AccountItem {
        return items[index]
    }
}