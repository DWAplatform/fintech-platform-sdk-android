package com.dwaplatform.android.account.payinpayoutfinancialdata.ui

import com.dwaplatform.android.api.NetHelper
import com.dwaplatform.android.card.api.PaymentCardAPI
import com.dwaplatform.android.card.db.PaymentCardPersistenceDB
import com.dwaplatform.android.iban.api.IbanAPI
import com.dwaplatform.android.iban.db.IbanPersistanceDB
import com.dwaplatform.android.iban.models.BankAccount
import com.dwaplatform.android.models.DataAccount
import javax.inject.Inject

/**
 * Created by ingrid on 18/01/18.
 */
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
        apiCard.getPaymentCards(configuration.accessToken, configuration.userId){ optcards, opterror ->

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