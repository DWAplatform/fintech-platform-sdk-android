package com.fintechplatform.android.account.payinpayoutfinancialdata.ui

import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.card.api.PaymentCardAPI
import com.fintechplatform.android.card.db.PaymentCardPersistenceDB
import com.fintechplatform.android.iban.api.IbanAPI
import com.fintechplatform.android.iban.db.IbanPersistanceDB
import com.fintechplatform.android.iban.models.BankAccount
import com.fintechplatform.android.models.DataAccount
import javax.inject.Inject

class PayInPayOutFinancialDataPresenter @Inject constructor(val view: PayInPayOutFinancialDataContract.View,
                                                            val apiCard: PaymentCardAPI,
                                                            val apiBankAccount: IbanAPI,
                                                            val configuration: DataAccount,
                                                            val ibanDB: IbanPersistanceDB,
                                                            val cardDB: PaymentCardPersistenceDB): PayInPayOutFinancialDataContract.Presenter {

    var isBankAccountLoaded: Boolean = false
    var isPaymentCardLoaded: Boolean = false

    override fun onBackwardClicked(){
        view.goBack()
    }

    override fun calcCardValue(): String? {
        val optcc = cardDB.load()
        return optcc?.let { cc ->
            "${cc.alias} ${cc.expiration}"
        }
    }

    override fun calcIBANValue(): String? {
        val optiban = ibanDB.load()
        return optiban?.iban
    }


    override fun initFinancialData() {
        ibanDB.load()?.let {
            view.setBankAccountText(it.iban?:"")
            isBankAccountLoaded = true
        }?: loadBankAccount()

        cardDB.load()?.let { cc ->
            view.setPaymentCardNumber("${cc.alias} ${cc.expiration}")
            isPaymentCardLoaded = true
        }?: loadPaymentCard()
    }

    override fun loadBankAccount() {
        ibanDB.delete()

        apiBankAccount.getbankAccounts(configuration.accessToken, configuration.userId) { optbankaccounts, opterror ->

            if (opterror != null) {
                return@getbankAccounts
            }
            if (optbankaccounts == null) {
                return@getbankAccounts
            }
            val bankaccounts = optbankaccounts
            bankaccounts.forEach { ba ->
                val iban = BankAccount(ba.bankaccountid, ba.iban, ba.activestate)
                ibanDB.save(iban)
            }
            isBankAccountLoaded = true
            refreshData()
        }
    }

    override fun loadPaymentCard() {
        apiCard.getPaymentCards(configuration.accessToken, configuration.userId, configuration.accountId, configuration.tenantId){ optcards, opterror ->

            if (opterror != null) {
                handleErrors(opterror)
                return@getPaymentCards
            }
            if (optcards == null) {
                return@getPaymentCards
            }
            val cards = optcards
            cards.forEach { c ->
                cardDB.replace(c)
            }
            isPaymentCardLoaded = true
            refreshData()
        }
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                view.showCommunicationInternalError()
        }
    }

    private fun refreshData() {
        if(!isPaymentCardLoaded || !isBankAccountLoaded)
            return
        initFinancialData()
    }
}