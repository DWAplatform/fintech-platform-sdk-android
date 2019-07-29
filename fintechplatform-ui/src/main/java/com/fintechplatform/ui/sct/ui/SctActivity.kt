package com.fintechplatform.ui.sct.ui

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
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
import com.fintechplatform.ui.money.MoneyValueInputFilter
import kotlinx.android.synthetic.main.activity_transfer.*
import java.util.*
import javax.inject.Inject


class SctActivity : AppCompatActivity(), SctContract.View {
    @Inject lateinit var presenter: SctContract.Presenter
    @Inject lateinit var alertHelpers: AlertHelpers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SctUI.instance.buildSctComponent(this, this).inject(this)
        setContentView(R.layout.activity_transfer)

        val name = intent.extras.getString("name") ?: ""
        val iban = intent.extras.getString("iban") ?: ""

        presenter.initializate(name, iban)

        amountText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onEditingChanged()
            }
        })

        val dateListener = DatePickerDialog.OnDateSetListener { x, year, monthOfYear, dayOfMonth ->
            presenter.onPickExecutionDate(year, monthOfYear, dayOfMonth)
        }

        executionDateText.setOnClickListener {
            val date = Calendar.getInstance()
            date.add(Calendar.YEAR, 0)

            showCalendar(date, dateListener)
        }

        amountText.filters = arrayOf<InputFilter>(MoneyValueInputFilter())

        forwardButton.setOnClickListener { presenter.onConfirm() }
        backwardButton.setOnClickListener { presenter.onAbort() }
    }

    override fun onResume() {
        super.onResume()
        presenter.refresh()
    }

    override fun enableForwardButton(isEnable: Boolean) {
        forwardButton.isEnabled = isEnable
    }

    override fun isInstantSCTChecked(): Boolean {
        return instant.isChecked
    }

    override fun isUrgentSCTChecked(): Boolean {
        return urgent.isChecked
    }

    override fun getAmountText(): String {
        return amountText.text.toString()
    }

    override fun getMessageText(): String {
        return messageText.text.toString()
    }

    override fun setExecutionDateText(executionDate: String) {
        executionDateText.setText(executionDate)
    }

    override fun getExecutionDateText(): String {
        return executionDateText.text.toString()
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

    private fun showCalendar(date: Calendar, dateListener: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(this, dateListener, date
                .get(Calendar.YEAR), date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)).show()
    }


    fun goToMain() {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        startActivity(intent)
        finish()
    }
}