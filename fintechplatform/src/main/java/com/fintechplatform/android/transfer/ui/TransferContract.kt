package com.fintechplatform.android.transfer.ui

/**
 * Created by ingrid on 15/09/17.
 */
interface TransferContract {
    interface View{
        fun enableForwardButton(isEnable: Boolean)
        fun getAmountText(): String
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

    interface Presenter{
        fun initialize(p2pUserId: String)
        fun onEditingChanged()
        fun onConfirm()
        fun onAbortClick()
        fun refresh()
    }
}