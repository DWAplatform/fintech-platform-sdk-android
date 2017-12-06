package com.dwafintech.dwapay.financial.payin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.dwafintech.dwapay.App
import com.dwafintech.dwapay.R
import com.dwafintech.dwapay.alert.AlertHelpers
import com.dwafintech.dwapay.financial.MoneyValueInputFilter
import com.dwafintech.dwapay.financial.creditcard.CreditCardActivity
import com.dwafintech.dwapay.financial.secure3d.Secure3DActivity
import com.dwafintech.dwapay.ui.WindowBarColor
import com.dwaplatform.android.R
import com.dwaplatform.android.models.MoneyValueInputFilter
import kotlinx.android.synthetic.main.activity_payin.*
import javax.inject.Inject

/**
 * Payment from user credit card to emoney
 */
class PayInActivity : FragmentActivity(), PayInContract.View {

    lateinit var alertHelpers: AlertHelpers
    lateinit var presenter: PayInContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payin)

        val initialAmount =
                if (intent.hasExtra("initialAmount"))
                    intent.getLongExtra("initialAmount", 0L)
                else null

        findViewById<EditText>(R.id.amountText).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onEditingChanged()
            }
        })

        findViewById<EditText>(R.id.amountText).filters = arrayOf<InputFilter>(MoneyValueInputFilter())

        findViewById<Button>(R.id.forwardButton).setOnClickListener {
            presenter.onConfirm()
        }

        findViewById<Button>(R.id.backwardButton).setOnClickListener {
            presenter.onAbortClick()
        }

        WindowBarColor.update(window, resources)

        presenter.initialize(initialAmount)

    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
    }

    override fun setForwardTextConfirm() {
        findViewById<Button>(R.id.forwardButton).text = resources.getString(R.string.confirm)
    }

    override fun setForwardButtonPayInCC() {
        findViewById<Button>(R.id.forwardButton).setText(resources.getString(R.string.payin_cc))
    }

    override fun setAmount(amount: String) {
        findViewById<TextView>(R.id.amountText).setText(amount)
    }

    override fun getAmount() : String {
        return findViewById<EditText>(R.id.amountText).text.toString()
    }

    override fun setForward(title: String) {
        findViewById<Button>(R.id.forwardButton).setText(title)
    }

    override fun forwardEnable() {
        findViewById<Button>(R.id.forwardButton).isEnabled = true
    }

    override fun forwardDisable() {
        findViewById<Button>(R.id.forwardButton).isEnabled = false
    }

    override fun setNewBalanceAmount(title: String) {
        findViewById<TextView>(R.id.newBalanceAmountLabel).text = title
    }

    override fun setFeeAmount(title: String) {
        findViewById<TextView>(R.id.feeAmountLabel).text = title
    }

    override fun showCommunicationWait() {
        findViewById<ProgressBar>(R.id.activityIndicator).visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        findViewById<ProgressBar>(R.id.activityIndicator).visibility = View.GONE
    }

    override fun showCommunicationInternalError() {
        alertHelpers.internalError(this).show()
    }

    override fun showIdempotencyError() {
        alertHelpers.idempotencyError(this).show()
    }

    override fun goToCreditCard() {
        startActivity(Intent(this, CreditCardActivity::class.java))
    }

    override fun goBack(){
        finish()
    }

    override fun goToSecure3D(redirecturl: String){
        val intent = Intent(this, Secure3DActivity::class.java)
        intent.putExtra("redirecturl", redirecturl)
        startActivity(intent)
        finish()
    }

    override fun showKeyboardAmount() {
        findViewById<EditText>(R.id.amountText).postDelayed({
            findViewById<EditText>(R.id.amountText).requestFocus()
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(findViewById<EditText>(R.id.amountText), 0)
        }, 300)
    }

    override fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }
}

