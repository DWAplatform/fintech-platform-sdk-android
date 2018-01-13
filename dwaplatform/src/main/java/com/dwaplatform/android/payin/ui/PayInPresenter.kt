package com.dwaplatform.android.payin

import com.dwaplatform.android.account.balance.helpers.BalanceHelper
import com.dwaplatform.android.account.balance.models.BalanceItem
import com.dwaplatform.android.card.db.PaymentCardPersistenceDB
import com.dwaplatform.android.models.DataAccount
import com.dwaplatform.android.money.FeeHelper
import com.dwaplatform.android.money.Money
import com.dwaplatform.android.money.MoneyHelper
import com.dwaplatform.android.payin.api.PayInAPI
import java.util.*
import javax.inject.Inject

class PayInPresenter @Inject constructor(val configuration: DataAccount,
                                         val view: PayInContract.View,
                                         val api: PayInAPI,
                                         val moneyHelper: MoneyHelper,
                                         val balanceHelper: BalanceHelper,
                                         val feeHelper: FeeHelper,
                                         val paymentCardpersistanceDB: PaymentCardPersistenceDB
)
    : PayInContract.Presenter {

    var idempotencyPayin: String? = null
    var token: String? = null

    override fun initialize(initialAmount: Long?) {
        idempotencyPayin = UUID.randomUUID().toString()

        initialAmount?.let {
            val money = Money(initialAmount)
            val strmoney = moneyHelper.toStringNoCurrency(money)
            view.setAmount(strmoney)
        }

        refreshConfirmButton()
        refreshData()
    }

    override fun refresh() {
        view.showKeyboardAmount()
        refreshConfirmButtonName()

        configuration.accountToken { newToken ->
            token = newToken
            reloadBalance()
            refreshConfirmButtonName()

        }
    }

    override fun onEditingChanged() {
        refreshConfirmButton()
        refreshData()
    }

    var retries = 0
    override fun onConfirm() {
        val paycard = paymentCardpersistanceDB.paymentCardId()
        if (paycard == null) {
            view.setForward("")
            view.goToCreditCard()
            return
        }
        val idempPayin = this.idempotencyPayin ?: return

        view.forwardDisable()
        view.showCommunicationWait()

        val money = Money.valueOf(view.getAmount())

        api.payIn(token!!,
                configuration.userId,
                configuration.accountId,
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

            retries = 0
            val payinreply = optpayinreply
            if (payinreply.securecodeneeded) {
                view.goToSecure3D(payinreply.redirecturl ?: "")
            } else {
                view.goBack()
            }
        }

    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is PayInAPI.IdempotencyError ->
                view.showIdempotencyError()
            is PayInAPI.TokenError ->
                if (retries > 2)
                    view.showCommunicationInternalError()
                else {
                    retries++
                    configuration.accountToken { opttoken ->

                        token = opttoken
                        onConfirm()

                        //TODO plus con timer.recall(1us, onConfirm)

                    }
                }
            else ->
                view.showCommunicationInternalError()
        }
    }

    override fun onAbortClick() {
        view.hideKeyboard()
        view.goBack()
    }

    private fun hasCreditCard(): Boolean {
        return paymentCardpersistanceDB.paymentCardId() != null
    }

    private fun refreshConfirmButtonName() {
        if (hasCreditCard()) {
            view.setForwardTextConfirm()
        } else {
            view.setForwardButtonPayInCC()
        }
    }

    private fun refreshConfirmButton() {
        // TODO if token == null allora disable confirm

        if ((view.getAmount().length) > 0)
            view.forwardEnable()
        else
            view.forwardDisable()
    }

    private fun reloadBalance() {
        balanceHelper.api.balance(token!!, configuration.userId, configuration.accountId) { optbalance, opterror ->
            if (opterror != null) {
                return@balance
            }

            if (optbalance == null) {
                return@balance
            }
            val balance = optbalance
            balanceHelper.persistence.saveBalance(BalanceItem(configuration.accountId, Money(balance)))

            refreshBalance()
        }
    }

    private fun refreshData() {
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


}
