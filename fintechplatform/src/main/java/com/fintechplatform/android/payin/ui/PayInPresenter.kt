package com.fintechplatform.android.payin

import com.fintechplatform.android.account.balance.helpers.BalanceHelper
import com.fintechplatform.android.account.balance.models.BalanceItem
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.card.api.PaymentCardRestAPI
import com.fintechplatform.android.card.db.PaymentCardPersistenceDB
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.money.FeeHelper
import com.fintechplatform.android.money.Money
import com.fintechplatform.android.money.MoneyHelper
import com.fintechplatform.android.payin.api.PayInAPI
import java.util.*
import javax.inject.Inject

class PayInPresenter @Inject constructor(val configuration: DataAccount,
                                         val view: PayInContract.View,
                                         val api: PayInAPI,
                                         val apiCardRest: PaymentCardRestAPI,
                                         val moneyHelper: MoneyHelper,
                                         val balanceHelper: BalanceHelper,
                                         val feeHelper: FeeHelper,
                                         val paymentCardpersistanceDB: PaymentCardPersistenceDB
)
    : PayInContract.Presenter {

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

        api.payIn(configuration.accessToken,
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
                return@payIn
            }

            if (optpayinreply == null) {
                view.showCommunicationInternalError()
                return@payIn
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
            balanceHelper.persistence.saveBalance(BalanceItem(configuration.accountId, balance))

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

        val newbalance = Money(bi.money.value + amountmoney.value)
        val newBalanceStr = moneyHelper.toString(newbalance)

        view.setNewBalanceAmount(newBalanceStr)
    }

    private fun refreshFee() {
        val amount = view.getAmount()
        val amountmoney = Money.valueOf(amount)

        val moneyFee: Money
        if (amountmoney.value == 0L) {
            moneyFee = Money(0)
        } else {
            val fee = feeHelper.calcPayInFee(amountmoney.value)
            moneyFee = Money(fee)
        }

        view.setFeeAmount(moneyHelper.toString(moneyFee))
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
}
