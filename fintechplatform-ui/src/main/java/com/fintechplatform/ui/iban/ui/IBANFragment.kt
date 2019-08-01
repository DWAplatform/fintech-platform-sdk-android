package com.fintechplatform.ui.iban.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.di.Injectable
import com.mukesh.countrypicker.CountryPicker
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_iban.view.*
import javax.inject.Inject


class IBANFragment: Fragment(), IBANContract.View, Injectable {
    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var presenter: IBANContract.Presenter

    var picker: CountryPicker? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_iban, container, false)

        //IbanUI.createIBANViewComponent(activity, this).inject(this)

        presenter.init()

        view.numberText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.addressText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })
        view.zipcodeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })
        view.cityText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.countryofresidenceText.setOnClickListener { presenter.onCountryOfResidenceClick()}

        view.confirmButton.setOnClickListener { presenter.onConfirm() }

        view.backwardButton.setOnClickListener { presenter.onAbortClick() }
        return view
    }

    override fun onResume() {
        super.onResume()
        setBackwardText()
        view?.numberText?.postDelayed({
            view?.numberText?.requestFocus()
            val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(view?.numberText, 0)
        }, 300)
    }

    override fun setAbortText() {
        view?.backwardButton?.text = resources.getString(R.string.navheader_abort)
    }

    override fun setBackwardText() {
        view?.backwardButton?.text = resources.getString(R.string.navheader_back)
    }

    override fun getNumberTextLength(): Int {
        return view?.numberText?.length() ?:0
    }

    override fun getAddressTextLength(): Int {
        return view?.addressText?.length() ?:0
    }

    override fun getZipcodeTextLength(): Int {
        return view?.zipcodeText?.length() ?:0
    }

    override fun getCityTextLength(): Int {
        return view?.cityText?.length()?: 0
    }

    override fun getCountryofresidenceTextLength(): Int {
        return view?.countryofresidenceText?.length() ?:0
    }

    override fun confirmButtonEnable(isEnable: Boolean) {
        view?.confirmButton?.isEnabled = isEnable
    }

    override fun setIBANText(iban: String) {
        view?.numberText?.setText(iban)
    }

    override fun setAddressText(address: String) {
        view?.addressText?.setText(address)
    }

    override fun setZipcodeText(ZIPcode: String) {
        view?.zipcodeText?.setText(ZIPcode)
    }

    override fun setCityText(city: String) {
        view?.cityText?.setText(city)
    }

    override fun setNumberText(iban: String) {
        view?.numberText?.setText(iban)
    }

    override fun setCountryofresidenceText(countryOfResidence: String) {
        view?.countryofresidenceText?.setText(countryOfResidence)
    }

    override fun getCountryofresidenceText(): String {
        return view?.countryofresidenceText?.text.toString()
    }

    override fun getAddressText(): String {
        return view?.addressText?.text.toString()
    }


    override fun getZipcodeText(): String {
        return view?.zipcodeText?.text.toString()
    }

    override fun getCityText(): String {
        return view?.cityText?.text.toString()
    }

    override fun getNumberText(): String {
        return view?.numberText?.text.toString()
    }

    override fun enableAllTexts(isEnable: Boolean) {
        view?.addressText?.isEnabled = isEnable
        view?.zipcodeText?.isEnabled = isEnable
        view?.cityText?.isEnabled = isEnable
        view?.countryofresidenceText?.isEnabled = isEnable
    }

    override fun showCountryPicker(){
        picker = CountryPicker.newInstance("Seleziona stato di residenza")

        picker?.setListener { name, code, dialCode, flagDrawableResID ->
            presenter.onCountrySelected(name, code)
        }
        picker?.show(activity.supportFragmentManager, "COUNTRY_PICKER")
    }

    override fun closeCountryPicker(){
        picker?.dismiss()
    }

    override fun showTokenExpiredWarning() {
        alertHelpers.tokenExpired(activity) { _, _->
            activity.finish()
        }
    }

    override fun showCommunicationWait() {
        view?.activityIndicator?.visibility = View.VISIBLE
    }

    override fun hideCommunicationWait() {
        view?.activityIndicator?.visibility = View.GONE
    }

    override fun showCommunicationInternalError() {
        alertHelpers.internalError(activity).show()

    }

    override fun hideSoftkeyboard() {
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun goBack() {
        activity.finish()
    }

    companion object {
        fun newInstance() : IBANFragment {
            return IBANFragment()
        }
    }
}