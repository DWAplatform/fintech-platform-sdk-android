package com.fintechplatform.android.transactions.models

open class TransactionsManager {

    private val items = mutableListOf<TransactionItem>()

    open fun count(): Int { return items.size }

    open fun initAll(items: List<TransactionItem>): Boolean {
        val lastorder = if (this.items.isEmpty())
            Long.MAX_VALUE else this.items[0].order

        this.items.clear()

        items.forEach { item ->
            val newitem =
                    if (item.order > lastorder)
                        TransactionItem(
                                item.id,
                                item.accountId,
                                item.what,
                                item.who,
                                item.message,
                                item.amount,
                                item.twhen,
                                item.order,
                                item.status,
                                item.error,
                                true)
                    else item
            this.items.add(newitem)
        }

        return true
    }

    fun add(item: TransactionItem) {
        // TODO: item with equals through id
        items.add(item)
    }

    fun set(item: TransactionItem, index: Int) {
        items[index] = item
    }

    open fun item(index: Int): TransactionItem {
        return items[index]
    }
}