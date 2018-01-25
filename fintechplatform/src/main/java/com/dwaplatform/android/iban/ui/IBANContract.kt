package com.dwaplatform.android.iban.ui

/**
 * Created by ingrid on 13/09/17.
 */
interface IBANContract {
    interface View{
        fun confirmButtonEnable(isEnable: Boolean)

        fun getCountryofresidenceText(): String
        fun getAddressText(): String
        fun getZipcodeText(): String
        fun getCityText(): String
        fun getNumberText(): String
        fun getNumberTextLength(): Int
        fun getAddressTextLength(): Int
        fun getZipcodeTextLength(): Int
        fun getCityTextLength(): Int
        fun getCountryofresidenceTextLength(): Int

        fun setIBANText(iban: String)
        fun setAddressText(address: String)
        fun setZipcodeText(ZIPcode: String)
        fun setCityText(city: String)
        fun setCountryofresidenceText(countryOfResidence: String)
        fun setAbortText()
        fun setNumberText(iban: String)
        fun setBackwardText()
        fun enableAllTexts(isEnable: Boolean)

        fun closeCountryPicker()
        fun showTokenExpiredWarning()
        fun showCountryPicker()
        fun showCommunicationWait()
        fun hideCommunicationWait()
        fun showCommunicationInternalError()
        fun hideSoftkeyboard()
        fun goBack()
    }

    interface Presenter{
        fun refreshConfirmButton()
        fun initIBAN()

        fun onCountryOfResidenceClick()
        fun onCountrySelected(name: String, code: String)
        fun onConfirm()
        fun onAbortClick()
    }
}