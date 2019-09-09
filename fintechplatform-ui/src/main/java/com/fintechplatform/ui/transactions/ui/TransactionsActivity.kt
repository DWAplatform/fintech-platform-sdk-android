package com.fintechplatform.ui.transactions.ui


import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.transactions.models.TransactionItem
import com.fintechplatform.ui.transactions.ui.detail.TransactionDetailFragment

/**
 * Transactions list view fragment
 */
class TransactionsActivity : FragmentActivity(), TransactionsContract.Navigation {
    private data class IntentContent(val hostName: String, val dataAccount: DataAccount)

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        var frag: TransactionsFragment? = null

        savedInstanceState?.let{
            frag = supportFragmentManager.findFragmentByTag(TransactionsFragment::class.java.canonicalName) as TransactionsFragment
        }

        getExtras()?.let {intent ->
            frag?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.contentContainer, it, TransactionsFragment::class.java.canonicalName)
                        .commit()
            }?:
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer, TransactionsFragment.newInstance(intent.hostName, intent.dataAccount), TransactionsFragment::class.java.canonicalName)
                    .commit()
        }
        setContentView(R.layout.activity_with_fragment)

    }

    private fun getExtras(): IntentContent? = intent.extras?.getString("hostname")?.let { hostname ->
        intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
            IntentContent(hostname, dataAccount)
        }
    }

    override fun goToTransactionsDetail(transaction: TransactionItem) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.contentContainer, TransactionDetailFragment.newInstance(transaction), TransactionDetailFragment::class.java.canonicalName)
                .addToBackStack(null)
                .commit()
    }

    //    override fun onHiddenChanged(hidden: Boolean) {
//        //todo super.onHiddenChanged(hidden) transactions activity
//        if (!hidden) {
//            presenter.currentTransactions()
//        }
//    }


}
