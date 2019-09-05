package com.fintechplatform.ui.payin

import android.util.Log
import com.fintechplatform.api.account.balance.models.BalanceItem
import com.fintechplatform.api.card.api.PaymentCardAPI
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.payin.api.PayInAPI
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper
import com.fintechplatform.ui.card.db.PaymentCardPersistenceDB
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.FeeHelper
import com.fintechplatform.ui.money.MoneyHelper
import java.util.*
import javax.inject.Inject

class PayInPresenter @Inject constructor(val configuration: DataAccount,
                                         val view: PayInContract.View,
                                         val api: PayInAPI,
                                         val apiCardRest: PaymentCardAPI,
                                         val moneyHelper: MoneyHelper,
                                         val balanceHelper: BalanceHelper,
                                         val feeHelper: FeeHelper,
                                         val paymentCardpersistanceDB: PaymentCardPersistenceDB
)
    : PayInContract.Presenter {

    var idempotencyPayin: String? = null

    override fun initialize(initialAmount: Long?) {

        initialAmount?.let {
            if(it == 0L)
                return@let
            else {
                val money = Money(initialAmount)
                val strmoney = moneyHelper.toStringNoCurrency(money)
                view.setAmount(strmoney)
            }
        }

        paymentCardpersistanceDB.deletePaymentCard()
        refreshConfirmButton()
        refreshData()
    }

    override fun refresh() {
        idempotencyPayin = UUID.randomUUID().toString()
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
            view.goToPaymentCard()
            return
        }
        val idempPayin = this.idempotencyPayin ?: return

        Log.d("IDEMPOTENT", idempPayin)
        view.forwardDisable()
        view.showCommunicationWait()
        view.hideKeyboard()

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
                Log.d("URL", payinreply.redirecturl)
                view.goTo3dSecure(payinreply.redirecturl ?: "")
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
            calcPayInFee(amountmoney)

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
            // todo use default payment card
            if(cards.isNotEmpty()) {
                paymentCardpersistanceDB.replace(cards[0])
            }
            refreshData()
        }
    }

    fun calcPayInFee(amount: Money) {
        val paycard = paymentCardpersistanceDB.paymentCardId(configuration.accountId) ?: return
        api.payInFee(configuration.accessToken,
                configuration.tenantId,
                configuration.accountId,
                configuration.ownerId,
                configuration.accountType,
                paycard,
                amount) { optfee, optexception ->
            if (optexception != null) {
                handleErrors(optexception)
                return@payInFee
            }
            if (optfee == null) {
                return@payInFee
            }

            view.setFeeAmount(moneyHelper.toString(optfee))
        }
    }
}
