package com.fintechplatform.ui.account.financialdata.payinpayout

import com.fintechplatform.api.card.api.PaymentCardRestAPI
import com.fintechplatform.api.iban.api.IbanAPI
import com.fintechplatform.api.iban.models.BankAccount
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB
import com.fintechplatform.ui.iban.db.IbanPersistanceDB
import com.fintechplatform.ui.models.DataAccount
import javax.inject.Inject

class PayInPayOutFinancialDataPresenter @Inject constructor(val view: FinancialDataContract.View,
                                                            val apiCardRest: PaymentCardRestAPI,
                                                            val apiBankAccount: IbanAPI,
                                                            val configuration: DataAccount,
                                                            val ibanDB: IbanPersistanceDB,
                                                            val cardDB: PaymentCardPersistenceDB): FinancialDataContract.Presenter {

    var isBankAccountLoaded: Boolean = false
    var isPaymentCardLoaded: Boolean = false

    override fun initialize() {
        view.enableIBAN(true)
        view.enablePaymentCard(true)
        view.showGoToEditArrow()
        ibanDB.delete()
        cardDB.deletePaymentCard()
    }

    override fun onBackwardClicked(){
        view.goBack()
    }

    override fun calcCardValue(): String? {
        val optcc = cardDB.load(configuration.accountId)
        return optcc?.let { cc ->
            "${cc.alias} ${cc.expiration}"
        }
    }

    override fun calcIBANValue(): String? {
        val optiban = ibanDB.load(configuration.accountId)
        return optiban?.iban
    }


    override fun initFinancialData() {
        ibanDB.load(configuration.accountId)?.let {
            view.setBankAccountText(it.iban?:"")
            isBankAccountLoaded = true
        }

        cardDB.load(configuration.accountId)?.let { cc ->
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
                val iban = BankAccount(ba.bankaccountid, ba.accountId, ba.iban, ba.activestate)
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

            // todo show default card
            if(cards.isNotEmpty()) {
                cardDB.replace(cards[0])
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