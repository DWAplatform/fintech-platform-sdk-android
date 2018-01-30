package com.fintechplatform.android.account.list.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.fintechplatform.android.R

import kotlinx.android.synthetic.main.activity_accountlist.*

class AccountlistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accountlist)

        recycler.layoutManager = LinearLayoutManager(this)
//        recycler.adapter = AccountsAdapter(this, ) //  TransactionsAdapter(this, manager) { transaction ->
//            presenter.transactionClick(transaction)
//        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Create new Account available soon", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
