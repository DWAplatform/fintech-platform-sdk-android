package com.dwaplatform.android.account.payinpayoutfinancialdata.ui

/**
 * Created by ingrid on 18/01/18.
 */
interface PayInPayOutFinancialDataContract {
    interface View {
        fun showTokenExpiredWarning()
        fun showCommunicationInternalError()
        fun setPaymentCardNumber(cardNumber: String)
        fun setBankAccountText(iban: String)
        fun goBack()
    }

    interface Presenter {
        fun loadPaymentCard()
        fun loadBankAccount()
        fun initFinancialData()
        fun onBackwardClicked()
        fun calcCardValue(): String?
        fun calcIBANValue(): String?
    }
}