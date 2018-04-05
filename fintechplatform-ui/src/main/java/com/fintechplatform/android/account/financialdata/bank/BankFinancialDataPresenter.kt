package com.fintechplatform.android.account.financialdata.bank

import com.fintechplatform.android.account.financialdata.payinpayout.FinancialDataContract
import com.fintechplatform.android.card.api.PaymentCardRestAPI
import com.fintechplatform.android.card.db.PaymentCardPersistenceDB
import com.fintechplatform.android.iban.api.IbanAPI
import com.fintechplatform.android.iban.db.IbanPersistanceDB
import com.fintechplatform.android.iban.models.BankAccount
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.net.NetHelper


class BankFinancialDataPresenter constructor(private val view: FinancialDataContract.View,
                                             private val apiCardRest: PaymentCardRestAPI,
                                             private val apiIBAN: IbanAPI,
                                             private val configuration: DataAccount,
                                             private val ibanDB: IbanPersistanceDB,
                                             private val cardDB: PaymentCardPersistenceDB): FinancialDataContract.Presenter {

    var isBankAccountLoaded: Boolean = false
    var isPaymentCardLoaded: Boolean = false

    override fun initialize() {
        view.enablePaymentCard(false)
        view.enableIBAN(false)
        view.hideGoToEditArrow()
        ibanDB.delete()
        cardDB.deletePaymentCard()
    }

    override fun onRefresh() {
        loadIban()
//        loadPaymentCard()
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

    override fun onBackwardClicked() {
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

    fun loadIban() {
        apiIBAN.getIBAN(configuration.accessToken,
                configuration.ownerId,
                configuration.accountId,
                configuration.accountType,
                configuration.tenantId) { optiban, opterror ->
            if (opterror != null) {
                handleErrors(opterror)
                return@getIBAN
            }
            if (optiban == null) {
                return@getIBAN
            }
            val iban = optiban
            val ibanBA = BankAccount("id",configuration.accountId, iban, null)
            ibanDB.replace(ibanBA)

            isBankAccountLoaded = true
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