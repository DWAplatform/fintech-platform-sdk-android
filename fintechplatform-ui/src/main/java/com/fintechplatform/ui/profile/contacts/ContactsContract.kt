package com.fintechplatform.ui.profile.contacts

interface ContactsContract {
    interface View {
        fun setAbortText()
        fun setBackwardText()
        fun getEmailText(): String
        fun getTelephoneText(): String
        fun setEmailText(email: String)
        fun setTelephoneText(phone: String)
        fun enableAllTexts(isEnables: Boolean)
        fun enableConfirmButton(isEnable: Boolean)
        fun showEmailError()
        fun showWaiting()
        fun showTokenExpiredWarning()
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
    }

    interface Navigation {
        fun backFromContacts()
    }
}