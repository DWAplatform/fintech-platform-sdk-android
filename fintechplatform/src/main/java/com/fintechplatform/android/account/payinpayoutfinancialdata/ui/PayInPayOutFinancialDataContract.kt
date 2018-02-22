package com.fintechplatform.android.account.payinpayoutfinancialdata.ui

interface PayInPayOutFinancialDataContract {
    interface View {
        fun enableIBAN(isEnable: Boolean)
        fun enablePaymentCard(isEnable: Boolean)
        fun showTokenExpiredWarning()
        fun showCommunicationInternalError()
        fun setPaymentCardNumber(cardNumber: String)
        fun setBankAccountText(iban: String)
        fun goBack()
    }

    interface Presenter {
        fun initialize()
        fun onRefresh()
        fun initFinancialData()
        fun onBackwardClicked()
        fun calcCardValue(): String?
        fun calcIBANValue(): String?
    }
}