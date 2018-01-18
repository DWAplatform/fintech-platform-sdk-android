package com.dwaplatform.android.account.financialdata.ui

/**
 * Created by ingrid on 18/01/18.
 */
interface FinancialDataContract {
    interface View {
        fun goBack()
    }

    interface Presenter {
        fun onBackwardClicked()
        fun calcCardValue(): String?
        fun calcIBANValue(): String?
    }
}