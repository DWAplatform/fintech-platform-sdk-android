package com.fintechplatform.ui.qrtransfer.qrconfirm

import android.content.Context
import android.media.RingtoneManager
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.images.ImageHelper
import kotlinx.android.synthetic.main.activity_payment_confirm.*
import javax.inject.Inject


/**
 * Requires to Confirm a Payment based on qrcode
 */
class QrConfirmActivity : FragmentActivity(), QrConfirmContract.View {

    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var imageHelper: ImageHelper
    @Inject lateinit var presenter: QrConfirmContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QrConfirmUI.instance.buildQrPaymentConfirmtComponent(this as Context, this).inject(this)
        setContentView(R.layout.activity_payment_confirm)

        val qrCode = intent.extras.getString("qrCode")

        presenter.initialize(qrCode)

        forwardButton.setOnClickListener { presenter.onConfirm() }
        backwardButton.setOnClickListener { presenter.onCancel() }
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun getContext(): Context {
        return this
    }

    override fun setLabelPersonFullName(fullName: String) {
        person_fullname.text = fullName
    }

    override fun labelPersonFullName(isVisible: Boolean) {
        person_fullname.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    override fun userIcon(isVisible: Boolean) {
        cvnav_user_icon.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    override fun setNewBalanceAmountLabel(amount: String) {
        newBalanceAmountLabel.text = amount
    }

    override fun setNewBalanceAmountColor(color: Int) {
        newBalanceAmountLabel.setTextColor(color)
    }

    override fun setAmountText(amount: String) {
        amountText.setText(amount)
    }

    override fun getAmountText(): String {
        return amountText.text.toString()
    }

    override fun setFeeAmount(fee: String) {
        feeAmountLabel.text = fee
    }

    override fun setForwardButtonEnable(isEnable: Boolean) {
        forwardButton.isEnabled = isEnable
    }

    override fun goToPayIn(amount: Long) {
        //todo cashInUI.startActivity(this, amount)
    }

    override fun setForwardButtonPayIn() {
        forwardButton.text = "Ricarica"
    }

    override fun setForwardTextConfirm() {
        forwardButton.text = resources.getString(R.string.confirm)
    }

    override fun showWaitingSpin() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaitingSpin() {
        activityIndicator.visibility = View.GONE
    }

    override fun showIdempotencyError() {
        alertHelpers.idempotencyError(this).show()
    }

    override fun showQrCodeError() {
        alertHelpers.qrCodeError(this) { _,_ ->
            finish()
        }
    }

    override fun showInternalError() {
        alertHelpers.internalError(this).show()
    }

    override fun showSuccessComunication() {
        alertHelpers.successGeneric(this, "Transazione avvenuta con successo!") { _, _ ->
            goToMain()
        }
    }

    override fun showTokenError() {
        alertHelpers.tokenExpired(this) { _,_ ->
            finish()
        }
    }

    override fun playSound() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(applicationContext, notification)
        r.play()
    }

    override fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun goBack() {
        finish()
    }

    override fun goToMain() {
        finish()
    }

//    override fun goToAuth(userid: String, completion: (String) -> Unit) {
//        AuthBuilder()
//                .createAuthUI(userid, completion)
//                .authUI.start(this)
//    }

    override fun refreshAmountData() {
        amountText.postDelayed({
            amountText.requestFocus()
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(amountText, 0)
        }, 300)
    }

    override fun setUserImage(photo: String?) {
        imageHelper.setImageViewUser(nav_user_icon, photo)
    }

}
