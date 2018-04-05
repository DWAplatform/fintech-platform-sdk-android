package com.fintechplatform.android.sct.ui


interface SctContract {
    interface View {
        fun setExecutionDateText(executionDate: String)
        fun getExecutionDateText(): String
        fun enableForwardButton(isEnable: Boolean)
        fun getAmountText(): String
        fun isUrgentSCTChecked(): Boolean
        fun isInstantSCTChecked(): Boolean
        fun setBalanceAmountText(amount: String)
        fun setPersonFullName(fullname: String)
        fun setPositiveBalanceColorText()
        fun setNegativeBalanceColorText()
        fun setFeeAmountText(fee: String)
        fun getMessageText(): String
        fun showCommunicationWait()
        fun hideCommunicationWait()
        fun showCommunicationInternalError()
        fun showTokenExpiredWarning()
        fun showIdempotencyError()
        fun playSound()
        fun showSuccessDialog()
        fun goBack()
        fun showKeyboardAmount()
        fun hideKeyboard()

    }

    interface Presenter {
        fun initializate(name: String, iban: String)
        fun onPickExecutionDate(year: Int, monthOfYear: Int, dayOfMonth: Int)
        fun onEditingChanged()
        fun onConfirm()
        fun onAbort()
        fun refresh()
    }
}