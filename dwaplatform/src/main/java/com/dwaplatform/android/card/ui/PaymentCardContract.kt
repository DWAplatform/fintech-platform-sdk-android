package com.dwaplatform.android.card.ui

/**
 * Created by ingrid on 13/12/17.
 */
interface PaymentCardContract {
    interface View {
        fun getNumberTextLength(): Int
        fun getDateTextLength(): Int
        fun getCCvTextLength(): Int
        fun confirmButtonEnable(isEnabled: Boolean)

        fun getNumberText(): String
        fun getDAteText(): String
        fun getCCvText(): String
        fun showCommunicationWait()
        fun hideCommunicationWait()

        fun showCommunicationInternalError()
        fun showKeyboard()
        fun hideKeyboard()
        fun goBack()
    }

    interface Presenter {
        fun initPaymentCard()
        fun refreshConfirmButton()
        fun onConfirm()
        fun onAbortClick()
        fun refresh()
    }
}