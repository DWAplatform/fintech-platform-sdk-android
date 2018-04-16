package com.fintechplatform.ui.cashin

import com.fintechplatform.api.account.balance.models.BalanceItem
import com.fintechplatform.api.card.api.PaymentCardRestAPI
import com.fintechplatform.api.cashin.api.CashInAPI
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.FeeHelper
import com.fintechplatform.ui.money.MoneyHelper
import java.util.*
import javax.inject.Inject

class CashInPresenter @Inject constructor(val configuration: DataAccount,
                                          val view: CashInContract.View,
                                          val api: CashInAPI,
                                          val apiCardRest: PaymentCardRestAPI,
                                          val moneyHelper: MoneyHelper,
                                          val balanceHelper: BalanceHelper,
                                          val feeHelper: FeeHelper,
                                          val paymentCardpersistanceDB: PaymentCardPersistenceDB
)
    : CashInContract.Presenter {

    var idempotencyPayin: String? = null

    override fun initialize(initialAmount: Long?) {
        idempotencyPayin = UUID.randomUUID().toString()

        initialAmount?.let {
            val money = Money(initialAmount)
            val strmoney = moneyHelper.toStringNoCurrency(money)
            view.setAmount(strmoney)
        }

        paymentCardpersistanceDB.deletePaymentCard() //TODO
        refreshConfirmButton()
        refreshData()
    }

    override fun refresh() {
        view.showKeyboardAmount()
        reloadBalance()
        loadPaymentCard()
    }

    override fun onEditingChanged() {
        refreshConfirmButton()
        refreshData()
    }

    override fun onConfirm() {
        val paycard = paymentCardpersistanceDB.paymentCardId(configuration.accountId)
        if (paycard == null) {
            view.setForward("")
            view.goToCreditCard()
            return
        }
        val idempPayin = this.idempotencyPayin ?: return

        view.forwardDisable()
        view.showCommunicationWait()

        val money = Money.valueOf(view.getAmount())

        api.cashIn(configuration.accessToken,
                configuration.ownerId,
                configuration.accountId,
                configuration.accountType,
                configuration.tenantId,
                paycard,
                money,
                idempPayin) { optpayinreply, opterror ->

            view.hideCommunicationWait()
            refreshConfirmButton()

            if (opterror != null) {
                handleErrors(opterror)
                return@cashIn
            }

            if (optpayinreply == null) {
                view.showCommunicationInternalError()
                return@cashIn
            }
            val payinreply = optpayinreply
            if (payinreply.securecodeneeded) {
                view.goToSecure3D(payinreply.redirecturl ?: "")
            } else {
                view.goBack()
            }
        }

    }

    override fun onAbortClick() {
        view.hideKeyboard()
        view.goBack()
    }

    private fun hasCreditCard(): Boolean {
        return paymentCardpersistanceDB.paymentCardId(configuration.accountId) != null
    }

    private fun refreshConfirmButtonName() {
        if (hasCreditCard()) {
            view.setForwardTextConfirm()
        } else {
            view.setForwardButtonPayInCC()
        }
    }

    private fun refreshConfirmButton() {
        if ((view.getAmount().length) > 0)
            view.forwardEnable()
        else
            view.forwardDisable()
    }

    private fun reloadBalance() {
        balanceHelper.api.balance(configuration.accessToken,
                configuration.ownerId,
                configuration.accountId,
                configuration.accountType,
                configuration.tenantId) { optbalance, opterror ->
            if (opterror != null) {
                return@balance
            }

            if (optbalance == null) {
                return@balance
            }
            val balance = optbalance
            balanceHelper.persistence.saveBalance(BalanceItem(balance.balance, balance.availableBalance), configuration.accountId)

            refreshBalance()
        }
    }


    fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.IdempotencyError ->
                view.showIdempotencyError()
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                view.showCommunicationInternalError()
        }
    }

    private fun refreshData() {
        refreshConfirmButtonName()
        refreshBalance()
        refreshFee()
    }

    private fun refreshBalance() {
        val optbi = balanceHelper.persistence.getBalanceItem(configuration.accountId) ?: return
        val bi = optbi

        val amount = view.getAmount()
        val amountmoney = Money.valueOf(amount)

        val newbalance = Money(bi.balance.value + amountmoney.value)
        val newBalanceStr = moneyHelper.toString(newbalance)

        view.setNewBalanceAmount(newBalanceStr)
    }

    private fun refreshFee() {
        val amount = view.getAmount()
        val amountmoney = Money.valueOf(amount)

        val moneyFee: Money
        if (amountmoney.value == 0L) {
            view.setFeeAmount(moneyHelper.toString(Money(0L)))
        } else {
//            val fee = feeHelper.calcPayInFee(amountmoney.value)
//            moneyFee = Money(fee)
            calcCashInFee(amountmoney)

        }

//        view.setFeeAmount(moneyHelper.toString(moneyFee))
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
                paymentCardpersistanceDB.replace(c)
            }
            refreshData()
        }
    }

    fun calcCashInFee(amount: Money) {
        val paycard = paymentCardpersistanceDB.paymentCardId(configuration.accountId) ?: return
        api.cashInFee(configuration.accessToken,
                configuration.tenantId,
                configuration.accountId,
                configuration.ownerId,
                configuration.accountType,
                paycard,
                amount) { optfee, optexception ->
            if (optexception != null) {
                handleErrors(optexception)
                return@cashInFee
            }
            if (optfee == null) {
                return@cashInFee
            }

            view.setFeeAmount(moneyHelper.toString(optfee))
        }
    }
}
