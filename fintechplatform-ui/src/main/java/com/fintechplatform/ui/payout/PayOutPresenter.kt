package com.fintechplatform.ui.payout

import com.fintechplatform.api.account.balance.models.BalanceItem
import com.fintechplatform.api.cashout.api.CashOutAPI
import com.fintechplatform.api.iban.api.IbanAPI
import com.fintechplatform.api.iban.models.BankAccount
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper
import com.fintechplatform.ui.iban.db.IbanPersistanceDB
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.FeeHelper
import com.fintechplatform.ui.money.MoneyHelper
import java.util.*
import javax.inject.Inject

class PayOutPresenter @Inject constructor(val configuration: DataAccount,
                                          val view: PayOutContract.View,
                                          val api: CashOutAPI,
                                          val linkedBankAPI: IbanAPI,
                                          val moneyHelper: MoneyHelper,
                                          val balanceHelper: BalanceHelper,
                                          val feeHelper: FeeHelper,
                                          val ibanPersistanceDB: IbanPersistanceDB
): PayOutContract.Presenter {
    var idempotencyPayout: String? = null

    override fun initialize() {
        idempotencyPayout = UUID.randomUUID().toString()
        ibanPersistanceDB.delete() //TODO for a better UX get linked bank by accountid
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
        reloadLinkedBank()
    }

    override fun onConfirm() {
        val bankAccountId = ibanPersistanceDB.bankAccountId(configuration.accountId)
        if (bankAccountId == null) {
            view.setForwardText("")
            view.startIBANActivity()
            return
        }
        val idemp = this.idempotencyPayout ?: return

        view.setForwardDisable()
        view.showCommunicationWait()

        val money = Money.valueOf(view.getAmount())

        api.cashOut(configuration.accessToken,
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
                return@cashOut
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
        return ibanPersistanceDB.bankAccountId(configuration.accountId) != null
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
        refreshConfirmButtonName()
    }

    fun getAmountMoney(): Money {
        val amount = view.getAmount()
        val amountmoney = Money.valueOf(amount)
        return amountmoney
    }

    fun calcBalance(): Money? {
        val optbi = balanceHelper.persistence.getBalanceItem(configuration.accountId) ?: return null
        val amountmoney = getAmountMoney()

        val newbalance = Money(optbi.balance.value - amountmoney.value)
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
            balanceHelper.persistence.saveBalance(BalanceItem(balance.balance, balance.availableBalance), configuration.accountId)

            refreshBalance()
        }
    }
    //TODO for a better UX get linked bank by accountid and delete api call
    private fun reloadLinkedBank() {
        linkedBankAPI.getLinkedBanks(configuration.accessToken, configuration.ownerId, configuration.accountId, configuration.accountType, configuration.tenantId) { optbankaccounts, opterror ->

            if (opterror != null) {
                return@getLinkedBanks
            }
            if (optbankaccounts == null) {
                return@getLinkedBanks
            }
            val bankaccounts = optbankaccounts
            bankaccounts.forEach { ba ->
                val iban = BankAccount(ba.bankaccountid, ba.accountId, ba.iban, ba.activestate)
                ibanPersistanceDB.replace(iban)
            }
//            isBankAccountLoaded = true
            refreshData()
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

        if (amountmoney.value == 0L) {
            view.setFeeAmountLabel(moneyHelper.toString(Money(0L)))
        } else {
            calcCashOutFee(amountmoney)
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

    fun calcCashOutFee(amount: Money) {
        val bankAccountId = ibanPersistanceDB.bankAccountId(configuration.accountId)?: return

        api.cashOutFee(configuration.accessToken,
                configuration.tenantId,
                configuration.accountId,
                configuration.ownerId,
                configuration.accountType,
                bankAccountId,
                amount) { optfee, optexception ->
            if (optexception != null) {
                handleErrors(optexception)
                return@cashOutFee
            }
            if (optfee == null) {
                return@cashOutFee
            }

            view.setFeeAmountLabel(moneyHelper.toString(optfee))
        }
    }
}