package com.fintechplatform.android.payout.ui

import com.fintechplatform.android.account.balance.helpers.BalanceHelper
import com.fintechplatform.android.account.balance.models.BalanceItem
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.iban.db.IbanPersistanceDB
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.money.FeeHelper
import com.fintechplatform.android.money.Money
import com.fintechplatform.android.money.MoneyHelper
import com.fintechplatform.android.payout.api.PayOutAPI
import java.util.*
import javax.inject.Inject

class PayOutPresenter @Inject constructor(val configuration: DataAccount,
                                          val view: PayOutContract.View,
                                          val api: PayOutAPI,
                                          val moneyHelper: MoneyHelper,
                                          val balanceHelper: BalanceHelper,
                                          val feeHelper: FeeHelper,
                                          val ibanPersistanceDB: IbanPersistanceDB
): PayOutContract.Presenter {
    var idempotencyPayout: String? = null

    override fun initialize() {
        idempotencyPayout = UUID.randomUUID().toString()

        refreshConfirmButton()
        refreshData()
    }

    override fun onEditingChanged() {
        refreshConfirmButton()
        refreshData()
    }

    override fun refresh() {
        view.showKeyboardAmount()
        refreshConfirmButtonName()
        reloadBalance()
    }

    override fun onConfirm() {
        val bankAccountId = ibanPersistanceDB.bankAccountId()
        if (bankAccountId == null) {
            view.setForwardText("")
            view.startIBANActivity()
            return
        }
        val idemp = this.idempotencyPayout ?: return

        view.setForwardDisable()
        view.showCommunicationWait()

        val money = Money.valueOf(view.getAmount())

        api.payOut(configuration.accessToken,
                configuration.ownerId,
                configuration.accountId,
                configuration.accountType,
                configuration.tenantId,
                bankAccountId,
                money.value,
                idemp) { opterror ->

            view.hideCommunicationWait()
            refreshConfirmButton()

            if (opterror != null) {
                handleErrors(opterror)
                return@payOut
            }

            view.goBack()
        }
    }

    override fun onAbortClick() {
        view.hideSoftkeyboard()
        view.goBack()
    }

    fun refreshConfirmButtonName() {
        if (hasBankAccount()) {
            view.setForwardTextConfirm()
        } else {
            view.setForwardTextPayIBAN()
        }
    }

    fun hasBankAccount(): Boolean {
        return ibanPersistanceDB.bankAccountId() != null
    }


    fun refreshConfirmButton() {
        val amountmoney = getAmountMoney()
        val newbalance = calcBalance() ?: return
        if(amountmoney.value > 0 && newbalance.value >= 0){
            view.setForwardEnable()
        } else {
            view.setForwardDisable()
        }
    }

    fun refreshData() {
        refreshBalance()
        refreshFee()
    }

    fun getAmountMoney(): Money {
        val amount = view.getAmount()
        val amountmoney = Money.valueOf(amount)
        return amountmoney
    }

    fun calcBalance(): Money? {
        val optbi = balanceHelper.persistence.getBalanceItem(configuration.accountId) ?: return null
        val amountmoney = getAmountMoney()

        val newbalance = Money(optbi.money.value - amountmoney.value)
        return newbalance
    }

    private fun reloadBalance() {

        balanceHelper.api.balance(configuration.accessToken, configuration.ownerId, configuration.accountId, configuration.accountType, configuration.tenantId) { optbalance, opterror ->
            if (opterror != null) {
                handleErrors(opterror)
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

    fun refreshBalance() {
        val newBalance = calcBalance() ?: return

        view.setBalanceAmountLabel(moneyHelper.toString(newBalance))

        if (newBalance.value >= 0)
            view.setBalanceColorPositive()
        else
            view.setBalanceColorNegative()

    }

    fun refreshFee() {
        val amountmoney = getAmountMoney()

        val moneyFee =
                if (amountmoney.value == 0L) {
                    Money(0)
                } else {
                    Money(feeHelper.calcPayOutFee(amountmoney.value))
                }

        view.setFeeAmountLabel(moneyHelper.toString(moneyFee))
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
}