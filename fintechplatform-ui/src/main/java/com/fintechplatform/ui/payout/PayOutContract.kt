package com.fintechplatform.ui.payout

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

    interface Navigation {
        fun backwardFromPayOut()
        fun goToIBANAddress()
    }
}