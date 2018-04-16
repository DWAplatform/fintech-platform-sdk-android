package com.fintechplatform.ui.sct.ui

import com.fintechplatform.ui.account.balance.helpers.BalancePersistence
import com.fintechplatform.ui.account.balance.models.BalanceItem
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.FeeHelper
import com.fintechplatform.ui.money.MoneyHelper
import com.fintechplatform.ui.sct.model.PeersIBANModel
import java.text.SimpleDateFormat
import java.util.*

class SctPresenter constructor(private val view: SctContract.View,
                               private val apiSCT: SctAPI,
                               private var apiBalance: BalanceAPI,
                               private val config: DataAccount,
                               private var balancePersistence: BalancePersistence,
                               private var moneyHelper: MoneyHelper,
                               private var feeHelper: FeeHelper): SctContract.Presenter {

    private var executionDate: String? = null
    private var idempotencySct: String? = null
    private var peerIBANModel: PeersIBANModel?=null

    override fun initializate(name: String, iban: String){
        idempotencySct = UUID.randomUUID().toString()

        peerIBANModel = PeersIBANModel(name, iban)

        refreshConfirmButton()
        refreshData()
    }

    override fun onEditingChanged() {
        refreshConfirmButton()
        refreshData()
    }

    override fun onConfirm() {
        val idemp = this.idempotencySct

        idemp?.let {
            peerIBANModel?.let { peerIban ->
                view.enableForwardButton(false)
                view.showCommunicationWait()
                val money = Money.valueOf(view.getAmountText())

                apiSCT.sctPayment(config.accessToken,
                        config.tenantId,
                        config.ownerId,
                        config.accountId,
                        config.accountType,
                        peerIban.iban,
                        peerIban.name,
                        money.value,
                        view.getMessageText(),
                        executionDate,
                        view.isUrgentSCTChecked(),
                        view.isInstantSCTChecked(),
                        it) { opterror ->

                    view.hideCommunicationWait()
                    refreshConfirmButton()

                    if (opterror != null) {
                        when (opterror) {
                            is NetHelper.IdempotencyError ->
                                view.showIdempotencyError()
                            is NetHelper.TokenError ->
                                view.showTokenExpiredWarning()
                            else ->
                                view.showCommunicationInternalError()
                        }
                        return@sctPayment
                    }

                    view.playSound()
                    view.showSuccessDialog()
                }
            }
        }
    }

    override fun onPickExecutionDate(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val b = Calendar.getInstance()
        b.set(Calendar.YEAR, year)
        b.set(Calendar.MONTH, monthOfYear)
        b.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val exeDate = SimpleDateFormat("dd/MM/yyyy", Locale.ITALY).format(b.time)
        view.setExecutionDateText(exeDate)
        val converted = SimpleDateFormat("yyyy-MM-dd", Locale.ITALY).format(b.time)
        executionDate = converted
        refreshConfirmButton()
    }

    override fun onAbort() {
        view.hideKeyboard()
        view.goBack()
    }

    override fun refresh() {
        view.showKeyboardAmount()

        // FIXME chiamata al server per peer full name
        peerIBANModel?.let {
            view.setPersonFullName(it.name)
        }
        reloadBalance()
    }

    fun refreshConfirmButton() {
        val amountmoney = getAmountMoney()
        val newbalance = calcBalance()

        newbalance?.let {
            view.enableForwardButton(amountmoney.value > 0 &&
                    newbalance.value >= 0 &&
                    !executionDate.isNullOrEmpty())
        }
    }

    fun getAmountMoney(): Money {
        val amount = view.getAmountText()
        val amountmoney = Money.valueOf(amount)
        return amountmoney
    }

    fun calcBalance(): Money? {
        val optbi = balancePersistence.getBalanceItem(config.accountId)
        val optnewbalance = optbi?.let {
            val amountmoney = getAmountMoney()

            Money(optbi.money.value - amountmoney.value)
        }
        return optnewbalance
    }

    fun refreshData() {
        refreshBalance()
        refreshFee()
    }

    fun refreshBalance() {
        val optnewbalance = calcBalance()
        optnewbalance?.let { newbalance ->
            view.setBalanceAmountText(moneyHelper.toString(newbalance))

            if (newbalance.value >= 0){
                view.setPositiveBalanceColorText()
            } else {
                view.setNegativeBalanceColorText()
            }
        }
    }

    fun refreshFee() {
        val amountmoney = getAmountMoney()

        val moneyFee =
                if (amountmoney.value == 0L) {
                    Money(0)
                } else {
                    Money(feeHelper.calcPayOutFee(amountmoney.value))
                }
        view.setFeeAmountText(moneyHelper.toString(moneyFee))
    }

    fun reloadBalance() {
        apiBalance.balance(config.accessToken, config.ownerId, config.accountId, config.accountType, config.tenantId) { optbalance, opterror ->
            if (opterror != null) {
                handleErrors(opterror)
                return@balance
            }

            if (optbalance == null) {
                return@balance
            }
            val balance = optbalance
            balancePersistence.saveBalance(BalanceItem(config.accountId, balance[0]))

            refreshBalance()
        }
    }

    private fun handleErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                view.showCommunicationInternalError()
        }
    }
}