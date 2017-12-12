package com.dwaplatform.android.payin

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
import com.dwaplatform.android.R
import com.dwaplatform.android.alert.AlertHelpers
import com.dwaplatform.android.models.MoneyValueInputFilter
import com.dwaplatform.android.payin.ui.PayInUI
import com.dwaplatform.android.secure3d.ui.Secure3DUI
import kotlinx.android.synthetic.main.activity_payin.*
import javax.inject.Inject

class PayInActivity : FragmentActivity(), PayInContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: PayInContract.Presenter
    @Inject lateinit var secure3DUI: Secure3DUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PayInUI.createPayInViewComponent(this as Context, this).inject(this)
        setContentView(R.layout.activity_payin)

        val initialAmount =
                if (intent.hasExtra("initialAmount"))
                    intent.getLongExtra("initialAmount", 0L)
                else null

        amountText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onEditingChanged()
            }
        })

        amountText.filters = arrayOf<InputFilter>(MoneyValueInputFilter())

        forwardButton.setOnClickListener {
            presenter.onConfirm()
        }

        backwardButton.setOnClickListener {
            presenter.onAbortClick()
        }

        // FIXME commented due to sdk refactor
        //WindowBarColor.update(window, resources)

        presenter.initialize(initialAmount)

    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
    }

    override fun setForwardTextConfirm() {
        forwardButton.text = resources.getString(R.string.confirm)
    }

    override fun setForwardButtonPayInCC() {
        forwardButton.text = resources.getString(R.string.payin_cc)
    }

    override fun setAmount(amount: String) {
        amountText.setText(amount)
    }

    override fun getAmount() : String {
        return amountText.text.toString()
    }

    override fun setForward(title: String) {
        forwardButton.text = title
    }

    override fun forwardEnable() {
        forwardButton.isEnabled = true
    }

    override fun forwardDisable() {
        forwardButton.isEnabled = false
    }

    override fun setNewBalanceAmount(title: String) {
        newBalanceAmountLabel.text = title
    }

    override fun setFeeAmount(title: String) {
        feeAmountLabel.text = title
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

    override fun showIdempotencyError() {
        alertHelpers.idempotencyError(this).show()
    }

    override fun goToCreditCard() {
        // FIXME commented due to sdk refactor
        //startActivity(Intent(this, CreditCardActivity::class.java))
    }

    override fun goBack(){
        finish()
    }

    override fun goToSecure3D(redirecturl: String){
        secure3DUI.start(this, redirecturl)


        // FIXME commented due to sdk refactor
        /*
        val intent = Intent(this, Secure3DActivity::class.java)
        intent.putExtra("redirecturl", redirecturl)
        startActivity(intent)
        finish()
        */
    }

    override fun showKeyboardAmount() {
        amountText.postDelayed({
            amountText.requestFocus()
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(amountText, 0)
        }, 300)
    }

    override fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }
}

