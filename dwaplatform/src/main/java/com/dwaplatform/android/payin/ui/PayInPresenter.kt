package com.dwaplatform.android.payin

import com.dwafintech.dwapay.model.Money
import com.dwaplatform.android.account.Account
import com.dwaplatform.android.account.balance.Balance
import com.dwaplatform.android.acquiringchannels.PaymentCard
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import com.dwaplatform.android.payin.api.PayInRestAPI
import java.util.*

/**
 * Created by ingrid on 07/09/17.
 */
class PayInPresenter constructor(val view: PayInContract.View,
                                 val api: PayInRestAPI,
                                 val account: Account,
                                 val balance: Balance,
                                 val moneyHelper: MoneyHelper,
                                 val feeHelper: FeeHelper,
                                 val paymentCard: PaymentCard)
    : PayInContract.Presenter {

    var idempotencyPayin: String? = null

    override fun initialize(initialAmount: Long?) {
        idempotencyPayin = UUID.randomUUID().toString()

        initialAmount?.let {
            val money = Money(initialAmount)
            val strmoney = moneyHelper.toStringNoCurrency(money)
            view.setAmount(strmoney)
        }

        refreshConfirmButton()
    }

    override fun refresh() {
        view.showKeyboardAmount()
        refreshConfirmButtonName()
        refreshAndReloadBalance()
        refreshFee()
    }

    override fun onEditingChanged() {
        refreshConfirmButton()
        refreshBalance()
        refreshFee()
    }

    override fun onConfirm() {
        val creditCard = paymentCard.id
        if (creditCard == null) {
            view.setForward("")
            view.goToCreditCard()
            return
        }
        val idempPayin = this.idempotencyPayin ?: return

        view.forwardDisable()
        view.showCommunicationWait()

        val money = Money.valueOf(view.getAmount())
        api.payIn(account, money) { optpayinreply, opterror ->

            view.hideCommunicationWait()
            refreshConfirmButton()

            if (opterror != null) {
//                when (opterror) {
//                    api.IdempotencyError ->
//                        view.showIdempotencyError()
//                    else ->
                        view.showCommunicationInternalError()
//                }
                return@payIn
            }

            if (optpayinreply == null) {
                // FIXME: Why idempotency error?
                view.showIdempotencyError()
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
        return paymentCard.id != null
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

    private var balanceMoney = Money(0)

    private fun refreshAndReloadBalance() {




        balanceMoney = balance.getBalance { optMoney, optException ->
            if (optException != null)
                return@getBalance
            if (optMoney == null)
                return@getBalance

            balanceMoney = optMoney
            refreshBalance()

        }
        refreshBalance()
    }

    private fun refreshBalance() {
        val amount = view.getAmount()
        val amountmoney = Money.valueOf(amount)

        val newbalance = Money(balanceMoney.value + amountmoney.value)
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