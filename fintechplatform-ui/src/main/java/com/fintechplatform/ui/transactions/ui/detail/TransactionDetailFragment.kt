package com.fintechplatform.ui.transactions.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintechplatform.ui.R
import com.fintechplatform.ui.email.SendEmailHelper
import com.fintechplatform.ui.transactions.models.TransactionItem
import com.fintechplatform.ui.transactions.ui.detail.di.TransactionDetailViewComponent
import kotlinx.android.synthetic.main.fragment_transactiondetail.*
import kotlinx.android.synthetic.main.fragment_transactiondetail.view.*
import javax.inject.Inject


open class TransactionDetailFragment: Fragment(), TransactionDetailContract.View {

    @Inject
    lateinit var emailHelper: SendEmailHelper
    @Inject
    lateinit var presenter: TransactionDetailContract.Presenter

    open fun createTransactionDetailComponent(view: TransactionDetailContract.View): TransactionDetailViewComponent {
        return TransactionDetailUI.Builder.buildTransactioDetailComponent(view)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transactiondetail, container, false)

        createTransactionDetailComponent(this).inject(this)

        view.helpButton.setOnClickListener { presenter.onHelp() }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transaction = arguments.getParcelable<TransactionItem>("transaction")
        presenter.initialize(transaction)
    }
    override fun sendEmail(transaction: TransactionItem) {
        emailHelper.sendTransactionHelp(context, transaction)
    }

    override fun showMessageContent(msg: String) {
        messageContent.visibility = View.VISIBLE
        message.text = msg
    }

    override fun setSource(who: String?) {
        this.whois.text = who
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

    companion object {
        fun newInstance(transaction: TransactionItem): TransactionDetailFragment {
            val frag = TransactionDetailFragment()
            val args = Bundle()
            args.putParcelable("transaction", transaction)
            frag.arguments = args
            return frag
        }
    }
}