package com.dwaplatform.android.payin

import com.dwaplatform.android.account.balance.helpers.BalanceHelper
import com.dwaplatform.android.account.balance.models.BalanceItem
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.Money
import com.dwaplatform.android.money.MoneyHelper
import com.dwaplatform.android.payin.api.PayInAPI
import com.dwaplatform.android.payin.models.PayInConfiguration
import java.util.*
import javax.inject.Inject

/**
 * Created by ingrid on 07/09/17.
 */
class PayInPresenter @Inject constructor(val configuration: PayInConfiguration,
                                         val view: PayInContract.View,
                                         val api: PayInAPI,
                                         val moneyHelper: MoneyHelper,
                                         val balanceHelper: BalanceHelper,
                                         //val dbUsersHelper: DBUsersHelper,
                                         val feeHelper: FeeHelper
                                         //val dbccardhelper: DBCreditCardsHelper
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

        refreshConfirmButton()
        refreshData()
    }

    override fun refresh() {
        view.showKeyboardAmount()
        refreshConfirmButtonName()
        reloadBalance()
    }

    override fun onEditingChanged() {
        refreshConfirmButton()
        refreshData()
    }

    override fun onConfirm() {
        if (configuration.paymentCardId == null) {
            view.setForward("")
            view.goToCreditCard()
            return
        }
        val idempPayin = this.idempotencyPayin ?: return

        view.forwardDisable()
        view.showCommunicationWait()

        val money = Money.valueOf(view.getAmount())

        api.payIn(configuration.userId,
                configuration.accountId,
                configuration.paymentCardId,
                money,
                idempPayin) { optpayinreply, opterror ->

            view.hideCommunicationWait()
            refreshConfirmButton()

            if (opterror != null) {
                when (opterror) {
                    is PayInAPI.IdempotencyError ->
                        view.showIdempotencyError()
                    else ->
                        view.showCommunicationInternalError()
                }
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
        return configuration.paymentCardId != null
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
        balanceHelper.api.balance(configuration.userId, configuration.accountId) { optbalance, opterror ->
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
