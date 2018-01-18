package com.dwaplatform.android.account.payinpayoutfinancialdata.ui

/**
 * Created by ingrid on 18/01/18.
 */
interface PayInPayOutFinancialDataContract {
    interface View {
        fun goBack()
    }

    interface Presenter {
        fun onBackwardClicked()
        fun calcCardValue(): String?
        fun calcIBANValue(): String?
    }
}