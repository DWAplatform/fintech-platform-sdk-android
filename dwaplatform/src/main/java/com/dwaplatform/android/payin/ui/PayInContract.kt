package com.dwaplatform.android.payin

import android.support.v7.app.AppCompatActivity

/**
 * Created by ingrid on 07/09/17.
 */
interface PayInContract {

    interface View {
        fun setForwardTextConfirm()

        fun setForwardButtonPayInCC()

        fun setAmount(amount: String)

        fun getAmount() : String

        fun setForward(title: String)

        fun forwardEnable()

        fun forwardDisable()

        fun setNewBalanceAmount(title: String)

        fun setFeeAmount(title: String)

        fun showCommunicationWait()

        fun hideCommunicationWait()

        fun showCommunicationInternalError()
        fun showTokenExpiredWarning()
        fun showIdempotencyError()

        fun goToCreditCard()

        fun goBack()

        fun goToSecure3D(redirecturl: String)

        fun showKeyboardAmount()

        fun hideKeyboard()
    }

    interface Presenter {
        fun onEditingChanged()

        fun onConfirm()

        fun onAbortClick()

        fun initialize(initialAmount: Long?)

        fun refresh()
    }


}