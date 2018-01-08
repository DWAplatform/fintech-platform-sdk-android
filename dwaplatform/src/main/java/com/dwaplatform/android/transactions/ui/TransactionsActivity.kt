package com.dwaplatform.android.transactions.ui


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import com.dwaplatform.android.R
import com.dwaplatform.android.transactions.models.TransactionItem
import com.dwaplatform.android.transactions.models.TransactionsManager
import com.dwaplatform.android.transactions.ui.transactionDetail.ui.TransactionDetailActivity
import com.dwaplatform.android.transactions.ui.transactionDetail.ui.TransactionDetailUI
import kotlinx.android.synthetic.main.activity_transactions.*
import javax.inject.Inject

/**
 * Transactions list view fragment
 */
class TransactionsActivity: FragmentActivity(), TransactionsContract.View {

    @Inject lateinit var manager: TransactionsManager
    @Inject lateinit var presenter: TransactionsContract.Presenter
    @Inject lateinit var transactionDetail: TransactionDetailUI

    private val notificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.refreshTransactions()
        }
    }
//
//    companion object {
//        fun newInstance(): TransactionsFragment {
//            return TransactionsFragment()
//        }
//    }
//
//    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        val view = inflater?.inflate(R.layout.activity_transactions, container, false)
//        App.buildTransactionsComponent(this, this).inject(this)
//        return view
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        TransactionsUI.instance.createTransactionsViewComponent(this, this).inject(this)

        swipeLayout.setOnRefreshListener { presenter.refreshTransactions() }

        listView.layoutManager = LinearLayoutManager(this)
        listView.adapter = TransactionsAdapter(this, manager) { transaction ->
            presenter.transactionClick(transaction)
        }
    }

    override fun onResume() {
        super.onResume()

        val filter = IntentFilter()
        //filter.addAction(DwapayFirebaseMessagingService.BROADCAST_PAYMENT)
        registerReceiver(notificationReceiver, filter)

        presenter.refreshTransactions()
        presenter.currentTransactions()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(notificationReceiver)
    }

//    override fun onHiddenChanged(hidden: Boolean) {
//        //todo super.onHiddenChanged(hidden) transactions activity
//        if (!hidden) {
//            presenter.currentTransactions()
//        }
//    }

    override fun showTransactions(trs: List<TransactionItem>) {
        swipeLayout.isRefreshing = false
        manager.initAll(trs)
        listView.adapter.notifyDataSetChanged()
    }

    override fun showTransactionDetail(transaction: TransactionItem) {
//        val intent = Intent(this, TransactionDetailActivity::class.java)
//        intent.putExtra("transaction", transaction)
//        startActivity(intent)
        transactionDetail.start(this, transaction)
    }

}
