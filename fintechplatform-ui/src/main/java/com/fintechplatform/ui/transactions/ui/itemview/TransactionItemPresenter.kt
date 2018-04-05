package com.fintechplatform.ui.transactions.ui.itemview

import com.fintechplatform.ui.transactions.models.TransactionItem
import javax.inject.Inject

/**
 * Transaction Item presenter
 */
class TransactionItemPresenter @Inject constructor(val view: TransactionItemContract.View)
    : TransactionItemContract.Presenter {

    override fun item(item: TransactionItem) {

        view.setTextWhen(item.twhen)
        view.setTextWhat(item.what)

        if (item.status != "SUCCEEDED" && item.status != "CREATED") {
            view.setErrorAmount(item.amount)
            view.showError()
        } else {
            view.hideError()
            //FIXME need amount value here, not its string format
            if (item.amount.contains("-")) {
                view.setNegativeAmount(item.amount)
            } else {
                view.setPositiveAmount(item.amount)
            }
        }

        if (item.newitem) view.newItemVisible() else view.newItemHide()

        if (item.what.contains("Ricarica")) {
            view.setIconCashIn()
        }

        if (item.what.contains("Prelievo")) {
            view.setIconCashOut()
        }

        if (item.what.contains("Trasferimento")) {
            view.setIconP2P()
        }
    }

    override fun userClick(item: TransactionItem, tuserClick: (TransactionItem) -> Unit) {
        tuserClick(item)
    }
}