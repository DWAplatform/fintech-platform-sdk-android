package com.fintechplatform.ui.card

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.card.di.PaymentCardViewComponent
import com.fintechplatform.ui.models.DataAccount
import kotlinx.android.synthetic.main.fragment_paymentcard.*
import kotlinx.android.synthetic.main.fragment_paymentcard.view.*
import javax.inject.Inject


open class PaymentCardFragment: Fragment(), PaymentCardContract.View {
    @Inject
    lateinit var alertHelpers: AlertHelpers
    @Inject
    lateinit var presenter: PaymentCardContract.Presenter

    var navigation: PaymentCardContract.Navigation?=null

    open fun createPaymentCardViewComponent(context: Context, v: PaymentCardContract.View, dataAccount: DataAccount, hostName: String, isSandbox: Boolean): PaymentCardViewComponent {
        return PaymentCardUI.Builder.buildPaymentCardComponent(context, v, hostName, dataAccount, isSandbox)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_paymentcard, container, false)

        arguments.getString("hostname")?.let { hostname ->
            arguments.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                arguments.getBoolean("isSandbox").let { isSandbox ->
                    createPaymentCardViewComponent(context, this, dataAccount, hostname, isSandbox).inject(this)
                }
            }
        }

        presenter.initPaymentCard()

        view.numberText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.dateText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.ccvText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.confirmButton.setOnClickListener {
            presenter.onConfirm()
        }

        view.backwardButton.setOnClickListener {
            presenter.onAbortClick()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? PaymentCardContract.Navigation)?.let {
            navigation = it
        } ?: Log.e(PaymentCardFragment::class.java.canonicalName, "PaymentCardContract.Navigation interface must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    override fun getNumberTextLength(): Int {
        return numberText.length()
    }

    override fun getDateTextLength(): Int {
        return dateText.length()
    }

    override fun getCCvTextLength(): Int {
        return ccvText.length()
    }

    override fun getNumberText(): String {
        return numberText.text.toString()
    }

    override fun getDAteText(): String {
        return dateText?.text.toString()
    }

    override fun getCCvText(): String {
        return ccvText?.text.toString()
    }

    override fun confirmButtonEnable(isEnabled: Boolean) {
        confirmButton?.isEnabled = isEnabled
    }

    override fun showCommunicationWait() {
        activityIndicator?.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        activityIndicator?.visibility = View.GONE
    }

    override fun showCommunicationInternalError() {
        alertHelpers.internalError(activity).show()
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(activity) { _, _ ->
            activity.finish()
        }
    }

    override fun hideKeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun showKeyboard() {
        numberText?.postDelayed({
            numberText?.requestFocus()
            val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(numberText, 0)
        }, 300)
    }

    override fun goBack() {
        navigation?.goBackwardFromPaymentCard()
    }

    companion object {
        fun newInstance(hostname: String, dataAccount: DataAccount, isSandbox: Boolean): PaymentCardFragment {
            val frag = PaymentCardFragment()
            val args = Bundle()
            args.putString("hostname", hostname)
            args.putParcelable("dataAccount", dataAccount)
            args.putBoolean("isSandbox", isSandbox)
            frag.arguments = args
            return frag
        }
    }
}