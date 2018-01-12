package com.dwaplatform.android.profile.address.ui

interface AddressContract {
    interface View {
        fun setAbortText()
        fun setBackwardText()
        fun getAddressText(): String
        fun getPostalCodeText(): String
        fun getCityText(): String
        fun getResidenceCountry(): String
        fun setAddressText(address: String)
        fun setPostalCodeText(zipcode: String)
        fun setCityText(city: String)
        fun setResidenceCountry(country: String)
        fun showCountryPicker()
        fun closeCountryPicker()
        fun enableAllTexts(isEnables: Boolean)
        fun enableConfirmButton(isEnable: Boolean)
        fun showCommunicationInternalNetwork()
        fun showWaiting()
        fun hideWaiting()
        fun hideKeyboard()
        fun goBack()
    }

    interface Presenter {
        fun initializate()
        fun onRefresh()
        fun onAbort()
        fun onConfirm()
        fun refreshConfirmButton()
        fun onCountryOfResidenceClick()
        fun onCountrySelected(name: String, code: String)
    }
}