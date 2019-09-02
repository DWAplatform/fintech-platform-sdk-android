package com.fintechplatform.ui.card.ui

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
import com.fintechplatform.ui.models.DataAccount
import kotlinx.android.synthetic.main.fragment_paymentcard.view.*
import javax.inject.Inject


class PaymentCardFragment: Fragment(), PaymentCardContract.View {
    @Inject
    lateinit var alertHelpers: AlertHelpers
    @Inject
    lateinit var presenter: PaymentCardContract.Presenter

    var navigation: PaymentCardContract.Navigator?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_paymentcard, container, false)


        arguments.getString("hostname")?.let { hostname ->
            arguments.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                arguments.getBoolean("isSandbox")?.let { isSandbox ->
                    (activity.application as PaymentCardUIFactory).createPaymentCardComponent(activity, this, dataAccount, hostname, isSandbox).inject(this)
                }
            }
        }

        presenter.initPaymentCard()

        view?.numberText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view?.dateText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view?.ccvText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view?.confirmButton?.setOnClickListener {
            presenter.onConfirm()
        }

        view?.backwardButton?.setOnClickListener {
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
        if(context is PaymentCardContract.Navigator) {
            navigation = context
        } else {
            Log.d(this.toString(), "Be care Navigation Interface is implemented in your Activity!!")
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    override fun getNumberTextLength(): Int {
        return view?.numberText?.length()?: 0
    }

    override fun getDateTextLength(): Int {
        return view?.dateText?.length()?: 0
    }

    override fun getCCvTextLength(): Int {
        return view?.ccvText?.length()?: 0
    }

    override fun getNumberText(): String {
        return view?.numberText?.text.toString()
    }

    override fun getDAteText(): String {
        return view?.dateText?.text.toString()
    }

    override fun getCCvText(): String {
        return view?.ccvText?.text.toString()
    }

    override fun confirmButtonEnable(isEnabled: Boolean) {
        view?.confirmButton?.isEnabled = isEnabled
    }

    override fun showCommunicationWait() {
        view?.activityIndicator?.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        view?.activityIndicator?.visibility = View.GONE
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
        view?.numberText?.postDelayed({
            view?.numberText?.requestFocus()
            val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(view?.numberText, 0)
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