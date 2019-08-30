package com.fintechplatform.ui.card.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.models.DataAccount
import kotlinx.android.synthetic.main.activity_paymentcard.*
import javax.inject.Inject

/**
 * UI for Credit card number, data, ccv
 */
class PaymentCardActivity: AppCompatActivity(), PaymentCardContract.View {
    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: PaymentCardContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // App.buildCreditCard(this as Context, this).inject(this)

        intent.extras?.getString("hostname")?.let { hostname ->
            intent.extras?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                intent.extras?.getBoolean("isSandbox")?.let {isSandbox ->
                    (application as PaymentCardUIFactory).createPaymentCardComponent(this, this, dataAccount, hostname, isSandbox).inject(this)
                }
            }
        }
        setContentView(R.layout.activity_paymentcard)

        presenter.initPaymentCard()

        numberText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        dateText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        ccvText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        confirmButton.setOnClickListener {
            presenter.onConfirm()
        }

        backwardButton.setOnClickListener {
            presenter.onAbortClick()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
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
        return dateText.text.toString()
    }

    override fun getCCvText(): String {
        return ccvText.text.toString()
    }

    override fun confirmButtonEnable(isEnabled: Boolean) {
        confirmButton.isEnabled = isEnabled
    }

    override fun showCommunicationWait() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        activityIndicator.visibility = View.GONE
    }

    override fun showCommunicationInternalError() {
        alertHelpers.internalError(this).show()
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(this, { _,_ ->
            finish()
        })
    }

    override fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun showKeyboard() {
        numberText.postDelayed({
            numberText.requestFocus()
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(numberText, 0)
        }, 300)
    }

    override fun goBack() {
        finish()
    }
}