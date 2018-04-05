package com.fintechplatform.android.enterprise.address.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import com.fintechplatform.android.R
import com.fintechplatform.android.alert.AlertHelpers
import com.mukesh.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_profile_address.*
import javax.inject.Inject


class EnterpriseAddressActivity : AppCompatActivity(), EnterpriseAddressContract.View {

    @Inject lateinit var presenter: EnterpriseAddressContract.Presenter
    @Inject lateinit var alertHelper: AlertHelpers

    var picker: CountryPicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EnterpriseAddressUI.instance.createAddressComponent(this as Context, this).inject(this)
        setContentView(R.layout.activity_enterprise_address)

        presenter.initializate()

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

        countryofresidenceText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        countryofresidenceText.setOnClickListener { presenter.onCountryOfResidenceClick()}

        backwardButton.setOnClickListener { presenter.onAbort() }

        confirmButton.setOnClickListener { presenter.onConfirm() }
    }

    override fun onBackPressed() {
        presenter.onAbort()
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun setBackwardText() {
        backwardButton.text = resources.getString(R.string.navheader_back)
    }

    override fun setAbortText() {
        backwardButton.text = resources.getString(R.string.abort)
    }

    override fun getAddressText(): String {
        return addressText.text.toString()
    }

    override fun getPostalCodeText(): String {
        return zipcodeText.text.toString()
    }

    override fun getCityText(): String {
        return cityText.text.toString()
    }

    override fun getResidenceCountry(): String {
        return countryofresidenceText.text.toString()
    }

    override fun setAddressText(address: String) {
        addressText.setText(address)
    }

    override fun setPostalCodeText(zipcode: String) {
        zipcodeText.setText(zipcode)
    }

    override fun setCityText(city: String) {
        cityText.setText(city)
    }

    override fun setResidenceCountry(country: String) {
        countryofresidenceText.setText(country)
    }

    override fun showCountryPicker(){
        picker = CountryPicker.newInstance("Seleziona lo stato")

        picker?.setListener { name, code, dialCode, flagDrawableResID ->
            presenter.onCountrySelected(name, code)
        }
        picker?.show(supportFragmentManager, "COUNTRY_PICKER")
    }

    override fun closeCountryPicker(){
        picker?.dismiss()
    }

    override fun enableAllTexts(isEnables: Boolean) {
        addressText.isEnabled = isEnables
        zipcodeText.isEnabled = isEnables
        cityText.isEnabled = isEnables
        countryofresidenceText.isEnabled = isEnables
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }

    override fun showTokenExpiredWarning() {
        alertHelper.tokenExpired(this, { _,_ ->
            finish()
        })
    }

    override fun showCommunicationInternalError() {
        alertHelper.internalError(this).show()
    }

    override fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun goBack() {
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }
}