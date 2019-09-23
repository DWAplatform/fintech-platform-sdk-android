package com.fintechplatform.ui.enterprise.info

interface EnterpriseInfoContract {
    interface View {
        fun setNameText(name: String)
        fun setEnterpriseType(surname: String)
        fun setAbortText()
        fun setBackwardText()
        fun getNameText(): String
        fun getEnterpriseType(): String
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
        fun onAbortClick()
    }

    interface Navigation {
        fun backFromBusinessInfo()
    }
}