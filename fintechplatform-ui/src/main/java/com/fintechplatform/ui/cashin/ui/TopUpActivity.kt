package com.fintechplatform.ui.cashin

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.card.ui.PaymentCardUI
import com.fintechplatform.ui.cashin.ui.CashInUI
import com.fintechplatform.ui.money.MoneyValueInputFilter
import com.fintechplatform.ui.secure3d.ui.Secure3DUI
import kotlinx.android.synthetic.main.activity_payin.*
import javax.inject.Inject

class TopUpActivity : AppCompatActivity(), CashInContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: CashInContract.Presenter
    @Inject lateinit var secure3DUI: Secure3DUI
    @Inject lateinit var paymentCard: PaymentCardUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CashInUI.createPayInViewComponent(this as Context, this).inject(this)
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

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(this) { _, _ ->
            finish()
        }
    }

    override fun goToCreditCard() {
        paymentCard.start(this)
    }

    override fun goBack(){
        finish()
    }

    override fun goToSecure3D(redirecturl: String){
        secure3DUI.start(this, redirecturl)
        finish()
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

