package com.fintechplatform.ui.transactions.ui.detail

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.transactions.models.TransactionItem

/**
 * Show transaction details and allows to send an email issue case.
 */
class TransactionDetailActivity: FragmentActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var frag: TransactionDetailFragment? = null

        savedInstanceState?.let{
            frag = supportFragmentManager.findFragmentByTag(TransactionDetailFragment::class.java.canonicalName) as TransactionDetailFragment
        }

        intent.extras?.getParcelable<TransactionItem>("transaction")?.let { transactionItem ->
            frag?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.contentContainer, it, TransactionDetailFragment::class.java.canonicalName)
                        .commit()
            }?:
            supportFragmentManager.beginTransaction()
                    .replace(R.id.contentContainer, TransactionDetailFragment.newInstance(transactionItem), TransactionDetailFragment::class.java.canonicalName)
                    .commit()
        }
        setContentView(R.layout.activity_with_fragment)

    }


}