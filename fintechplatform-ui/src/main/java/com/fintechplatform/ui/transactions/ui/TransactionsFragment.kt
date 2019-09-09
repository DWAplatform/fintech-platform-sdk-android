package com.fintechplatform.ui.transactions.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.transactions.di.TransactionsViewComponent
import com.fintechplatform.ui.transactions.models.TransactionItem
import com.fintechplatform.ui.transactions.models.TransactionsManager
import kotlinx.android.synthetic.main.fragment_transactions.*
import kotlinx.android.synthetic.main.fragment_transactions.view.*
import javax.inject.Inject


open class TransactionsFragment: Fragment(), TransactionsContract.View  {

    @Inject lateinit var manager: TransactionsManager
    @Inject lateinit var presenter: TransactionsContract.Presenter
    @Inject lateinit var alerthelper: AlertHelpers

    var navigation: TransactionsContract.Navigation? = null

    private val notificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.refreshTransactions()
        }
    }

    open fun craeteTransactionsViewComponent(context: Context, view :TransactionsContract.View, hostname: String, dataAccount: DataAccount): TransactionsViewComponent {
        return TransactionsUI.Builder.buildTransactionsViewComponent(context, view, hostname, dataAccount)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transactions, container, false)

        arguments.getString("hostname")?.let { hostname ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let{ dataAccount ->
                craeteTransactionsViewComponent(context, this, hostname, dataAccount).inject(this)
            }
        }

        view.swipeLayout.setOnRefreshListener { presenter.refreshTransactions() }

        view.listView.layoutManager = LinearLayoutManager(context)
        view.listView.adapter = TransactionsAdapter(context, manager) { transaction ->
            presenter.transactionClick(transaction)
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? TransactionsContract.Navigation)?.let {
            navigation = it
        }?: Log.e(TransactionsFragment::class.java.canonicalName, "TransactionsContract.Navigation interface must be implemented in your Activity!!")
    }

    override fun onResume() {
        super.onResume()

        val filter = IntentFilter()
        filter.addAction("BROADCAST_PAYMENT")
        activity.registerReceiver(notificationReceiver, filter)

        presenter.refreshTransactions()
        presenter.currentTransactions()
    }

    override fun onPause() {
        super.onPause()
        activity.unregisterReceiver(notificationReceiver)
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }
    override fun showTokenExpired() {
        alerthelper.tokenExpired(context) { _, _ ->
            activity.finish()
        }
    }

    override fun showErrors(message: String?) {
        alerthelper.genericError(context, "transazione non caricata", message?: "qualcosa è andato storto")
    }

    override fun showTransactions(trs: List<TransactionItem>) {
        swipeLayout.isRefreshing = false
        manager.initAll(trs)
        listView.adapter.notifyDataSetChanged()
    }

    override fun showTransactionDetail(transaction: TransactionItem) {
        navigation?.goToTransactionsDetail(transaction)
    }

    companion object{
        fun newInstance(hostName: String, dataAccount: DataAccount): TransactionsFragment {
            val frag = TransactionsFragment()
            val args = Bundle()
            args.putString("hostname", hostName)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}