package com.fintechplatform.android.profile.lightdata.ui

interface LightDataContract {
    interface View {
        fun setNameText(name: String)
        fun setSurnameTect(surname: String)
        fun setBirthdayText(birthday: String)
        fun setNationalityText(nationality: String)
        fun setAbortText()
        fun setBackwardText()
        fun getNameText(): String
        fun getSurnameText(): String
        fun getNationalityText(): String
        fun getDateOfBirthText(): String
        fun enableAllTexts(areEnables: Boolean)
        fun enableConfirmButton(isEnable: Boolean)
        fun showTokenExpired()
        fun showWaiting()
        fun hideWaiting()
        fun hideKeyboard()
        fun goBack()
    }

    interface Presenter {
        fun onConfirm()
        fun initialize()
        fun onRefresh()
        fun refreshConfirmButton()
        fun onPickBirthdayDate(year: Int, monthOfYear: Int, dayOfMonth: Int)
        fun onPickCountryNationality(name: String, code: String)
        fun onAbortClick()
    }
}