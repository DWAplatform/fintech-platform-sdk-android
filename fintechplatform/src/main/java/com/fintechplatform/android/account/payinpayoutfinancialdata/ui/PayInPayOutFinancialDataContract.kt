package com.fintechplatform.android.account.payinpayoutfinancialdata.ui

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