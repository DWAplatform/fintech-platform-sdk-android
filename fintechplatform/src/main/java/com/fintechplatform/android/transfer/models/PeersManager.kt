package com.fintechplatform.android.transfer.models


class PeersManager {
    private val items = mutableListOf<PeersModel>()

    fun count(): Int { return items.size }

    fun initAll(items: List<PeersModel>): Boolean {
        // TODO: return true if there are new items added

        this.items.clear()
        this.items.addAll(items)

        return true
    }

    fun add(item: PeersModel) {
        // TODO: item with equals through id
        items.add(item)
    }

    fun set(item: PeersModel, index: Int) {
        items[index] = item
    }

    fun item(index: Int): PeersModel {
        return items[index]
    }

}