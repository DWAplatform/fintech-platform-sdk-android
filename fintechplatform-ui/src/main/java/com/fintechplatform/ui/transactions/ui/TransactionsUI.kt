package com.fintechplatform.ui.transactions.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.transactions.api.TransactionsAPIModule
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.transactions.di.*
import com.fintechplatform.ui.transactions.models.TransactionItem
import com.fintechplatform.ui.transactions.ui.detail.TransactionDetailActivity
import com.fintechplatform.ui.transactions.ui.detail.TransactionDetailFragment
import com.fintechplatform.ui.transactions.ui.itemview.TransactionItemViewHolder

class TransactionsUI(val hostName: String,
                     val configuration: DataAccount) {

    fun startActivity(context: Context) {
        val intent = Intent(context, TransactionsActivity::class.java)
        val args = Bundle()
        args.putString("hostname", hostName)
        args.putParcelable("dataAccount", configuration)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun startDetailActivity(context: Context, transaction: TransactionItem) {
        val intent = Intent(context, TransactionDetailActivity::class.java)
        val args = Bundle()
        args.putParcelable("transaction", transaction)
        intent.putExtras(args)
        context.startActivity(intent)
    }

    fun createFragment(): TransactionsFragment {
        return TransactionsFragment.newInstance(hostName, configuration)
    }

    fun createDetailFragment(transaction: TransactionItem): TransactionDetailFragment {
        return TransactionDetailFragment.newInstance(transaction)
    }

    object Builder {
        fun buildTransactionsViewComponent(context: Context, view: TransactionsContract.View, hostname: String, configuration: DataAccount): TransactionsViewComponent {
            return DaggerTransactionsViewComponent.builder()
                    .netModule(NetModule(Volley.newRequestQueue(context), hostname))
                    .transactionsPresenterModule(TransactionsPresenterModule(view, configuration))
                    .transactionsAPIModule(TransactionsAPIModule(hostname))
                    .build()
        }


        fun buildTransactionItemComponent(v: TransactionItemViewHolder): TransactionItemComponent {
            return DaggerTransactionItemComponent.builder()
                    .transactionItemPresenterModule(TransactionItemPresenterModule(v))
                    .build()
        }

        fun buildTransactionsHelper(): TransactionsHelperComponent {
            return DaggerTransactionsHelperComponent.builder().build()
        }
    }
}
