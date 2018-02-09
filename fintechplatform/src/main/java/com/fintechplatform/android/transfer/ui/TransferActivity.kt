package com.fintechplatform.android.transfer.ui

import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.fintechplatform.android.R
import com.fintechplatform.android.alert.AlertHelpers
import com.fintechplatform.android.images.ImageHelper
import com.fintechplatform.android.money.MoneyValueInputFilter
import kotlinx.android.synthetic.main.activity_transfer_p2p.*
import javax.inject.Inject

/**
 * Peer to Peer transfer, based on p2p user selected
 */
class TransferActivity: FragmentActivity(), TransferContract.View {
    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var imageHelper: ImageHelper
    @Inject lateinit var presenter: TransferContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TransferUI.instance.buildTransferComponent(this, this).inject(this)

        setContentView(R.layout.activity_transfer_p2p)

        val p2pUserID = intent.extras.getString("p2pid") ?: ""
        val p2pAccountId = intent.extras.getString("p2pAccountId") ?: ""
        val p2pTenantId = intent.extras.getString("p2pTenantId") ?: ""

        presenter.initialize(p2pUserID, p2pAccountId, p2pTenantId)

        amountText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onEditingChanged()
            }
        })

        amountText.filters = arrayOf<InputFilter>(MoneyValueInputFilter())

        forwardButton.setOnClickListener { presenter.onConfirm() }
        backwardButton.setOnClickListener { presenter.onAbortClick() }

    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
    }

    override fun enableForwardButton(isEnable: Boolean) {
        forwardButton.isEnabled = isEnable
    }

    override fun getAmountText(): String {
        return amountText.text.toString()
    }

    override fun getMessageText(): String {
        return messageText.text.toString()
    }

    override fun setBalanceAmountText(amount: String) {
        newBalanceAmountLabel.text = amount
    }

    override fun setPersonFullName(fullname: String) {
        person_fullname.text = fullname
    }

    override fun setPositiveBalanceColorText() {
        newBalanceAmountLabel.setTextColor(Color.BLACK)
    }

    override fun setNegativeBalanceColorText() {
        newBalanceAmountLabel.setTextColor(Color.RED)
    }

    override fun setFeeAmountText(fee: String) {
        feeAmountLabel.text = fee
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

    override fun playSound() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(this, notification)
        r.play()
    }

    override fun showSuccessDialog() {
        alertHelpers.successGeneric(this, "Transazione avvenuta con successo!") { _,_ ->
            goToMain()
        }
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(this) { _,_ ->
            finish()
        }
    }

    override fun goBack() {
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

    fun goToMain() {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        startActivity(intent)
        finish()
    }
}