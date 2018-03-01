package com.fintechplatform.android.qrtransfer.creditui

/**
 * Created by ingrid on 18/09/17.
 */
interface QrReceiveActivityContract{
    interface View{
        fun showAmountFragment()
        fun showShowFragmentWithTransactionId(transactionid: String)
        fun goBack()
    }

    interface Presenter{
        fun createAmountFragment()
    }
}