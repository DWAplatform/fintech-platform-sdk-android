package com.fintechplatform.android.payout.ui

/**
 * Created by ingrid on 11/09/17.
 */
interface PayOutContract {
    interface View{
        fun getAmount():String
        fun setForwardText(text: String)
        fun setForwardTextConfirm()
        fun setForwardTextPayIBAN()
        fun setForwardEnable()
        fun setForwardDisable()
        fun setBalanceAmountLabel(newBalance: String)
        fun setBalanceColorPositive()
        fun setBalanceColorNegative()
        fun setFeeAmountLabel(newFee: String)
        fun startIBANActivity()
        fun showKeyboardAmount()
        fun showCommunicationWait()
        fun hideCommunicationWait()
        fun showCommunicationInternalError()
        fun showTokenExpiredWarning()
        fun showIdempotencyError()
        fun hideSoftkeyboard()
        fun goBack()
    }

    interface Presenter{
        fun initialize()
        fun refresh()
        fun onEditingChanged()
        fun onConfirm()
        fun onAbortClick()

    }
}