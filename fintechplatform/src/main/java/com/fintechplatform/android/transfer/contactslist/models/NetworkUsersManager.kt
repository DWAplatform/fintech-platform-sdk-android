package com.fintechplatform.android.transfer.contactslist.models


class NetworkUsersManager {
    private val items = mutableListOf<NetworkUserModel>()

    fun count(): Int { return items.size }

    fun initAll(items: List<NetworkUserModel>): Boolean {
        // TODO: return true if there are new items added

        this.items.clear()
        this.items.addAll(items)

        return true
    }

    fun add(item: NetworkUserModel) {
        // TODO: item with equals through id
        items.add(item)
    }

    fun set(item: NetworkUserModel, index: Int) {
        items[index] = item
    }

    fun item(index: Int): NetworkUserModel {
        return items[index]
    }

}