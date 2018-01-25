package com.fintechplatform.android.transactions.ui.itemview

import com.fintechplatform.android.transactions.models.TransactionItem
import javax.inject.Inject

/**
 * Transaction Item presenter
 */
class TransactionItemPresenter @Inject constructor(val view: TransactionItemContract.View)
    : TransactionItemContract.Presenter {

    override fun item(item: TransactionItem) {

        view.setTextWhen(item.twhen)
        view.setTextWhat(item.what)
        view.setTextWho(item.who)

        if (item.status != "SUCCEEDED") {
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
    }

    override fun userClick(item: TransactionItem, tuserClick: (TransactionItem) -> Unit) {
        tuserClick(item)
    }
}