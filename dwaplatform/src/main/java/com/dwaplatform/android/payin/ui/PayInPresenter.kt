package com.dwafintech.dwapay.financial.payin

import com.dwafintech.dwapay.api.DWApayAPI
import com.dwafintech.dwapay.model.*
import com.dwaplatform.android.account.Account
import com.dwaplatform.android.acquiringchannels.PaymentCard
import com.dwaplatform.android.models.FeeHelper
import com.dwaplatform.android.models.MoneyHelper
import com.dwaplatform.android.payin.api.PayInAPI
import com.dwaplatform.android.user.User
import java.util.*
import javax.inject.Inject

/**
 * Created by ingrid on 07/09/17.
 */
class PayInPresenter constructor(val view: PayInContract.View,
                                 val api: PayInAPI,
                                 val moneyHelper: MoneyHelper,
                                 val feeHelper: FeeHelper)
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
        val creditCard = PaymentCard(Account(User())).id
        if (creditCard == null) {
            view.setForward("")
            view.goToCreditCard()
            return
        }
        val idempPayin = this.idempotencyPayin ?: return

        view.forwardDisable()
        view.showCommunicationWait()

        val money = Money.valueOf(view.getAmount())
        api.payIn()
//        { optpayinreply, opterror ->
//
//            view.hideCommunicationWait()
//            refreshConfirmButton()
//
//            if (opterror != null) {
//                when (opterror) {
//                    is DWApayAPI.IdempotencyError ->
//                        view.showIdempotencyError()
//                    else ->
//                        view.showCommunicationInternalError()
//                }
//                return@payIn
//            }
//
//            if (optpayinreply == null) {
//                // FIXME: Why idempotency error?
//                view.showIdempotencyError()
//                return@payIn
//            }
//            val payinreply = optpayinreply
//            if (payinreply.securecodeneeded) {
//                view.goToSecure3D(payinreply.redirecturl ?: "")
//            } else {
//                view.goBack()
//            }
//        }
    }

    override fun onAbortClick() {
        view.hideKeyboard()
        view.goBack()
    }

    private fun hasCreditCard(): Boolean {
        return PaymentCard(Account(User())).id != null
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

        api.balance(dbUsersHelper.userid()) { optbalance, opterror ->
            if (opterror != null) {
                return@balance
            }

            if (optbalance == null) {
                return@balance
            }
            val balance = optbalance
            dbBalanceHelper.saveBalance(BalanceItem(balance))

            refreshBalance()
        }
    }

    private fun refreshData() {
        refreshBalance()
        refreshFee()
    }

    private fun refreshBalance() {
        val optbi = dbBalanceHelper.getBalanceItem() ?: return
        val bi = optbi

        val amount = view.getAmount()
        val amountmoney = Money.valueOf(amount)

        val newbalance = Money(bi.balance + amountmoney.value)
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