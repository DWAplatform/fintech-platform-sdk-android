package com.fintechplatform.ui.transactions.ui.detail.ui

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import com.fintechplatform.ui.R
import com.fintechplatform.ui.email.SendEmailHelper
import com.fintechplatform.ui.transactions.models.TransactionItem
import kotlinx.android.synthetic.main.activity_transactiondetail.*
import javax.inject.Inject

/**
 * Show transaction details and allows to send an email issue case.
 */
class TransactionDetailActivity: FragmentActivity(), TransactionDetailContract.View {

    @Inject lateinit var emailHelper: SendEmailHelper
    @Inject lateinit var presenter: TransactionDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransactionDetailUI.instance.buildTransactioDetailComponent(this).inject(this)
        setContentView(R.layout.activity_transactiondetail)

        val transaction = intent.extras.get("transaction") as TransactionItem

        //WindowBarColor.update(window, resources)

        helpButton.setOnClickListener { presenter.onHelp() }

        presenter.initialize(transaction)
    }

    override fun sendEmail(transaction: TransactionItem) {
        emailHelper.sendTransactionHelp(this, transaction)
    }

    override fun showMessageContent(msg: String) {
        messageContent.visibility = View.VISIBLE
        message.text = msg
    }

    override fun setSource(who: String?) {
        this.who.text = who
    }

    override fun setTransactionWhat(what: String?) {
        this.what.text = what
    }

    override fun setTransactionAmount(amount: String?) {
        this.amount.text = amount
    }

    override fun setTransactionTwhen(twhen: String?) {
        this.twhen.text = twhen
    }

    override fun setTransactionStateDescription(stateDescription: String) {
        status.text = stateDescription
    }

    override fun setTransactionResultDescription(resultDescription: String) {
        result.text = resultDescription
    }
}