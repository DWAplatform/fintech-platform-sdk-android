package com.fintechplatform.android.account.payinpayoutfinancialdata.ui

import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.card.api.PaymentCardRestAPI
import com.fintechplatform.android.card.db.PaymentCardPersistenceDB
import com.fintechplatform.android.iban.api.IbanAPI
import com.fintechplatform.android.iban.db.IbanPersistanceDB
import com.fintechplatform.android.iban.models.BankAccount
import com.fintechplatform.android.models.DataAccount
import javax.inject.Inject

class PayInPayOutFinancialDataPresenter @Inject constructor(val view: PayInPayOutFinancialDataContract.View,
                                                            val apiCardRest: PaymentCardRestAPI,
                                                            val apiBankAccount: IbanAPI,
                                                            val configuration: DataAccount,
                                                            val ibanDB: IbanPersistanceDB,
                                                            val cardDB: PaymentCardPersistenceDB): PayInPayOutFinancialDataContract.Presenter {

    var isBankAccountLoaded: Boolean = false
    var isPaymentCardLoaded: Boolean = false

    override fun initialize() {
        view.enableIBAN(true)
        view.enablePaymentCard(true)
    }

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
        }

        cardDB.load()?.let { cc ->
            view.setPaymentCardNumber("${cc.alias} ${cc.expiration}")
            isPaymentCardLoaded = true
        }
    }

    override fun onRefresh() {
        loadBankAccount()
        loadPaymentCard()
    }

    private fun loadBankAccount() {
        apiBankAccount.getLinkedBanks(configuration.accessToken, configuration.ownerId, configuration.accountId, configuration.accountType, configuration.tenantId) { optbankaccounts, opterror ->

            if (opterror != null) {
                return@getLinkedBanks
            }
            if (optbankaccounts == null) {
                return@getLinkedBanks
            }
            val bankaccounts = optbankaccounts
            bankaccounts.forEach { ba ->
                val iban = BankAccount(ba.bankaccountid, ba.iban, ba.activestate)
                ibanDB.replace(iban)
            }
            isBankAccountLoaded = true
            refreshData()
        }
    }

    private fun loadPaymentCard() {
        apiCardRest.getPaymentCards(configuration.accessToken, configuration.ownerId, configuration.accountId, configuration.accountType, configuration.tenantId){ optcards, opterror ->

            if (opterror != null) {
                handleErrors(opterror)
                return@getPaymentCards
            }
            if (optcards == null) {
                return@getPaymentCards
            }
            val cards = optcards
            cards.forEach { c ->
                cardDB.savePaymentCard(c)
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