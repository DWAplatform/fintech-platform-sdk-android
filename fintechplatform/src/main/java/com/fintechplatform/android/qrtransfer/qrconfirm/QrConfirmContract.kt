package com.fintechplatform.android.qrtransfer.qrconfirm

import android.content.Context

/**
 * Created by ingrid on 18/09/17.
 */
interface QrConfirmContract {
    interface View{
        fun labelPersonFullName(isVisible: Boolean)
        fun userIcon(isVisible: Boolean)
        fun setNewBalanceAmountLabel(amount: String)
        fun setNewBalanceAmountColor(color: Int)
        fun getAmountText(): String
        fun setFeeAmount(fee: String)
        fun setForwardButtonEnable(isEnable: Boolean)
        fun showWaitingSpin()
        fun hideWaitingSpin()
        fun showIdempotencyError()
        fun showInternalError()
        fun showQrCodeError()
        fun showSuccessComunication()
        fun showTokenError()
        fun playSound()
        fun refreshAmountData()
        fun setLabelPersonFullName(fullName: String)
        fun setAmountText(amount: String)
        fun setUserImage(photo: String?)
        fun hideKeyboard()
        fun goToPayIn(amount: Long)
        fun goToMain()
        fun setForwardButtonPayIn()
        fun setForwardTextConfirm()
        fun goBack()
        fun getContext(): Context
    }

    interface Presenter{
        fun initialize(qrcode: String)
        fun onConfirm()
        fun onCancel()
        fun onRefresh()
    }
}