package com.fintechplatform.ui.payout.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.iban.ui.IbanUI
import com.fintechplatform.ui.money.MoneyValueInputFilter
import kotlinx.android.synthetic.main.activity_payout.*
import javax.inject.Inject

/**
 * Payment from user emoney to registered bank account
 */
class PayOutActivity: FragmentActivity(), PayOutContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: PayOutContract.Presenter
    @Inject lateinit var ibanUI: IbanUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PayOutUI.createPayOutViewComponent(this as Context, this).inject(this)
        setContentView(R.layout.activity_payout)

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

        presenter.initialize()
    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
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
        // TODO fix hostname and data account obj
       // ibanUI.startActivity(this, hostName, dataAccount)
    }

    override fun showKeyboardAmount() {
        amountText.postDelayed({
            amountText.requestFocus()
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
        alertHelpers.internalError(this).show()
    }

    override fun showIdempotencyError() {
        alertHelpers.idempotencyError(this).show()
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(this, { _,_ ->
            finish()
        })
    }

    override fun hideSoftkeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun goBack() {
        finish()
    }
}