package com.fintechplatform.ui.qrtransfer.creditui

/**
 * Created by ingrid on 15/09/17.
 */
interface QrReceiveAmountContract {
    interface View{
        fun getAmountTextLength(): Int
        fun getAmountText(): String
        fun getMessagetext(): String?
        fun setForwardButtonEnable(isEnabled: Boolean)
        fun showCommunicationWait()
        fun hideCommunicationWait()
        fun showCommunicationInternalError()
        fun showTokenExpiredWarning()
        fun hideKeyboard()
        fun showKeyboardAmount()

    }

    interface Presenter{
        fun setListener(listener: QrReceiveAmountFragmentPresenter.QrReceiveFragmentListener)
        fun removeListener()
        fun refreshConfirmButton()
        fun onConfirm()
        fun onAbortClick()
    }
}