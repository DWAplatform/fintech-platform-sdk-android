package com.dwaplatform.android.payout.ui

import com.dwaplatform.android.account.balance.helpers.BalanceHelper
import com.dwaplatform.android.auth.keys.KeyChain
import com.dwaplatform.android.iban.db.IbanPersistanceDB
import com.dwaplatform.android.models.DataAccount
import com.dwaplatform.android.money.FeeHelper
import com.dwaplatform.android.money.Money
import com.dwaplatform.android.money.MoneyHelper
import com.dwaplatform.android.payout.api.PayOutAPI
import java.util.*
import javax.inject.Inject

/**
 * Created by ingrid on 11/09/17.
 */

class PayOutPresenter @Inject constructor(val configuration: DataAccount,
                                          val view: PayOutContract.View,
                                          val api: PayOutAPI,
                                          val moneyHelper: MoneyHelper,
                                          val balanceHelper: BalanceHelper,
                                          val feeHelper: FeeHelper,
                                          val key: KeyChain,
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
        refreshBalance()
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
        api.payOut(key["tokenuser"],
                configuration.userId,
                configuration.accountId,
                money.value,
                idemp) { opterror ->

            view.hideCommunicationWait()
            refreshConfirmButton()

            if (opterror != null) {
                when (opterror) {
                    is PayOutAPI.IdempotencyError ->
                        view.showIdempotencyError()
                    else ->
                        view.showCommunicationInternalError()
                }
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
}