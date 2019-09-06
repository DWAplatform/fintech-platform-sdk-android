package com.fintechplatform.ui.payout

import android.content.Context
import android.graphics.Color
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
import com.fintechplatform.ui.payout.di.PayOutViewComponent
import kotlinx.android.synthetic.main.fragment_payout.*
import kotlinx.android.synthetic.main.fragment_payout.view.*
import javax.inject.Inject

/**
 * Payment from user emoney to registered bank account
 */
open class PayOutFragment: Fragment(), PayOutContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: PayOutContract.Presenter

    var navigation: PayOutContract.Navigation? = null
    
    open fun createPayOutViewComponent(context: Context, view: PayOutContract.View, hostName: String, dataAccount: DataAccount): PayOutViewComponent {
        return PayOutUI.Builder.buildPayOutViewComponent(context, this, hostName, dataAccount)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payout, container, false)
     
        arguments.getString("hostname")?.let { hostname -> 
            arguments?.getParcelable<DataAccount>("dataAccount")?.let{ dataAccount ->
                createPayOutViewComponent(context, this, hostname, dataAccount).inject(this)
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

        presenter.initialize()
    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? PayOutContract.Navigation)?.let {
            navigation = it
        }?: Log.e(PayOutFragment::class.java.canonicalName, "PayOutContract.Navigation interface must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    override fun getAmount(): String {
        return amountText.text.toString()
    }
    
    override fun setForwardText(text: String) {
        forwardButton.text = ""
    }

    override fun setForwardEnable() {
        forwardButton.isEnabled = true
    }

    override fun setForwardDisable() {
        forwardButton.isEnabled = false
    }

    override fun setBalanceAmountLabel(newBalance: String) {
        newBalanceAmountLabel.text = newBalance
    }

    override fun setBalanceColorPositive() {
        newBalanceAmountLabel.setTextColor(Color.BLACK)
    }

    override fun setBalanceColorNegative() {
        newBalanceAmountLabel.setTextColor(Color.RED)
    }

    override fun setFeeAmountLabel(newFee: String) {
        feeAmountLabel.text = newFee
    }

    override fun setForwardTextConfirm() {
        forwardButton.text = resources.getString(R.string.confirm)
    }

    override fun setForwardTextPayIBAN() {
        forwardButton.text = resources.getString(R.string.payout_iban)
    }

    override fun startIBANActivity() {
        navigation?.goToIBANAddress()
    }

    override fun goBack() {
        navigation?.backforwardFromPayOut()
    }

    override fun showKeyboardAmount() {
        amountText.postDelayed({
            amountText.requestFocus()
            val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(amountText, 0)
        }, 300)
    }

    override fun showCommunicationWait() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        activityIndicator.visibility = View.GONE
    }

    override fun showCommunicationInternalError() {
        alertHelpers.internalError(context).show()
    }

    override fun showIdempotencyError() {
        alertHelpers.idempotencyError(context).show()
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(context) { _,_ ->  activity.finish() }
    }

    override fun hideSoftkeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }
    
    companion object {
        fun newInstance(hostName: String, dataAccount: DataAccount): PayOutFragment {
            val frag = PayOutFragment()
            val args = Bundle()
            args.putString("hostname", hostName)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}