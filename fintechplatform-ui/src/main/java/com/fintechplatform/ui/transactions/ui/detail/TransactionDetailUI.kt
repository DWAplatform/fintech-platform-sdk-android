package com.fintechplatform.ui.transactions.ui.detail

import android.content.Context
import android.content.Intent
import com.fintechplatform.ui.transactions.models.TransactionItem
import com.fintechplatform.ui.transactions.ui.detail.di.DaggerTransactionDetailViewComponent
import com.fintechplatform.ui.transactions.ui.detail.di.TransactionDetailPresenterModule
import com.fintechplatform.ui.transactions.ui.detail.di.TransactionDetailViewComponent

class TransactionDetailUI {

    fun start(context: Context, item: TransactionItem) {
        val intent = Intent(context, TransactionDetailActivity::class.java)
        intent.putExtra("transaction", item)
        context.startActivity(intent)
    }

    object Builder {

        fun buildTransactioDetailComponent(view: TransactionDetailContract.View): TransactionDetailViewComponent {
            return DaggerTransactionDetailViewComponent.builder()
                    .transactionDetailPresenterModule(TransactionDetailPresenterModule(view))
                    .build()
        }
    }
}