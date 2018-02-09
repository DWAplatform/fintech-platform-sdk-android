package com.fintechplatform.android.sample.contactslist.models


class NetworkAccountsManager {
    private val items = mutableListOf<NetworkAccounts>()

    fun count(): Int { return items.size }

    fun initAll(items: List<NetworkAccounts>): Boolean {
        // TODO: return true if there are new items added

        this.items.clear()
        this.items.addAll(items)

        return true
    }

    fun add(item: NetworkAccounts) {
        // TODO: item with equals through id
        items.add(item)
    }

    fun set(item: NetworkAccounts, index: Int) {
        items[index] = item
    }

    fun item(index: Int): NetworkAccounts {
        return items[index]
    }

}