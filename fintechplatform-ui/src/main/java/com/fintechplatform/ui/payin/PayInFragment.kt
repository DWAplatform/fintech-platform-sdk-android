package com.fintechplatform.ui.payin.di

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.MoneyValueInputFilter
import com.fintechplatform.ui.payin.PayInContract
import kotlinx.android.synthetic.main.fragment_payin.*
import kotlinx.android.synthetic.main.fragment_payin.view.*
import javax.inject.Inject

class PayInFragment: Fragment(), PayInContract.View {
    @Inject
    lateinit var alertHelpers: AlertHelpers
    @Inject
    lateinit var presenter: PayInContract.Presenter

    var navigation: PayInContract.Navigation? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payin, container, false)

        arguments.getString("hostname")?.let { hostname ->
            arguments.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                arguments.getBoolean("isSandbox").let { isSandbox ->
                    (activity.application as? PayInUIFactory)?.let {
                        it.createPayInViewComponent(context, this, dataAccount, hostname, isSandbox).inject(this)
                    }?: Log.e(PayInFragment::class.java.canonicalName, "PayInFactory interface must be implemented in your Application")
                }
            }
        }

        view.amountText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onEditingChanged()
            }
        })

        view.amountText.filters = arrayOf<InputFilter>(MoneyValueInputFilter())

        view.forwardButton.setOnClickListener {
            presenter.onConfirm()
        }

        view.backwardButton.setOnClickListener {
            presenter.onAbortClick()
        }
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val initialAmount = arguments.getLong("initialAmount")
        presenter.initialize(initialAmount)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? PayInContract.Navigation)?.let {
            navigation = it
        }?: Log.e(PayInFragment::class.java.canonicalName, "PayInContract.Navigation interface must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
    }

    override fun setForwardTextConfirm() {
        forwardButton?.text = resources.getString(R.string.confirm)
    }

    override fun setForwardButtonPayInCC() {
        forwardButton?.text = resources.getString(R.string.payin_cc)
    }

    override fun setAmount(amount: String) {
        amountText.setText(amount)
    }

    override fun getAmount() : String {
        return amountText?.text.toString()
    }

    override fun setForward(title: String) {
        forwardButton?.text = title
    }

    override fun forwardEnable() {
        forwardButton?.isEnabled = true
    }

    override fun forwardDisable() {
        forwardButton?.isEnabled = false
    }

    override fun setNewBalanceAmount(title: String) {
        newBalanceAmountLabel?.text = title
    }

    override fun setFeeAmount(title: String) {
        feeAmountLabel?.text = title
    }

    override fun showCommunicationWait() {
        activityIndicator?.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        activityIndicator?.visibility = View.GONE
    }

    override fun showCommunicationInternalError() {
        alertHelpers.internalError(context).show()
    }

    override fun showIdempotencyError() {
        alertHelpers.idempotencyError(context).show()
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(context) { _, _ ->
            activity.finish()
        }
    }

    override fun goBack(){
        navigation?.goBackwardFromPayIn()
    }

    override fun goToPaymentCard() {
        navigation?.goToPaymentCard()
    }

    override fun goTo3dSecure(redirecturl: String) {
        navigation?.goTo3dSecure(redirecturl)
    }

    override fun showKeyboardAmount() {
        amountText.postDelayed({
            amountText.requestFocus()
            val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(amountText, 0)
        }, 300)
    }

    override fun hideKeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    companion object {
        fun newInstance(hostname: String, dataAccount: DataAccount, isSandbox: Boolean, amount: Long?) : PayInFragment {
            val frag = PayInFragment()
            val args = Bundle()
            args.putString("hostname", hostname)
            args.putParcelable("dataAccount", dataAccount)
            args.putBoolean("isSandbox", isSandbox)
            amount?.let {  args.putLong("initialAmount", amount) }
            frag.arguments = args
            return frag
        }
    }
}