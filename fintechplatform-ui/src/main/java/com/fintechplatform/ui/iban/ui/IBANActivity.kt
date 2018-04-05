package com.fintechplatform.ui.iban.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.mukesh.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_iban.*

import javax.inject.Inject

/**
 * UI for IBAN account number and mandatory personal data linked to.
 */
class IBANActivity: FragmentActivity(), IBANContract.View{
    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: IBANContract.Presenter

    var picker: CountryPicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IbanUI.createIBANViewComponent(this, this).inject(this)
        setContentView(R.layout.activity_iban)

        presenter.init()

        numberText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        addressText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })
        zipcodeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })
        cityText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        countryofresidenceText.setOnClickListener { presenter.onCountryOfResidenceClick()}

        confirmButton.setOnClickListener { presenter.onConfirm() }

        backwardButton.setOnClickListener { presenter.onAbortClick() }
    }

    override fun onResume() {
        super.onResume()
        setBackwardText()
        numberText.postDelayed({
            numberText.requestFocus()
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(numberText, 0)
        }, 300)
    }

    override fun setAbortText() {
        backwardButton.text = resources.getString(R.string.navheader_abort)
    }

    override fun setBackwardText() {
        backwardButton.text = resources.getString(R.string.navheader_back)
    }

    override fun getNumberTextLength(): Int {
        return numberText.length()
    }

    override fun getAddressTextLength(): Int {
        return addressText.length()
    }

    override fun getZipcodeTextLength(): Int {
        return zipcodeText.length()
    }

    override fun getCityTextLength(): Int {
        return cityText.length()
    }

    override fun getCountryofresidenceTextLength(): Int {
        return countryofresidenceText.length()
    }

    override fun confirmButtonEnable(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun setIBANText(iban: String) {
        numberText.setText(iban)
    }

    override fun setAddressText(address: String) {
        addressText.setText(address)
    }

    override fun setZipcodeText(ZIPcode: String) {
        zipcodeText.setText(ZIPcode)
    }

    override fun setCityText(city: String) {
        cityText.setText(city)
    }

    override fun setNumberText(iban: String) {
        numberText.setText(iban)
    }

    override fun setCountryofresidenceText(countryOfResidence: String) {
        countryofresidenceText.setText(countryOfResidence)
    }

    override fun getCountryofresidenceText(): String {
        return countryofresidenceText.text.toString()
    }

    override fun getAddressText(): String {
        return addressText.text.toString()
    }


    override fun getZipcodeText(): String {
        return zipcodeText.text.toString()
    }

    override fun getCityText(): String {
        return cityText.text.toString()
    }

    override fun getNumberText(): String {
        return numberText.text.toString()
    }

    override fun enableAllTexts(isEnable: Boolean) {
        addressText.isEnabled = isEnable
        zipcodeText.isEnabled = isEnable
        cityText.isEnabled = isEnable
        countryofresidenceText.isEnabled = isEnable
    }

    override fun showCountryPicker(){
        picker = CountryPicker.newInstance("Seleziona stato di residenza")

        picker?.setListener { name, code, dialCode, flagDrawableResID ->
            presenter.onCountrySelected(name, code)
        }
        picker?.show(supportFragmentManager, "COUNTRY_PICKER")
    }

    override fun closeCountryPicker(){
        picker?.dismiss()
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(this, { _,_->
            finish()
        })
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

    override fun hideSoftkeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun goBack() {
        finish()
    }
}