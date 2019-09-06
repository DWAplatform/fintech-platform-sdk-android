package com.fintechplatform.ui.transfer

import com.fintechplatform.api.account.balance.api.BalanceAPI
import com.fintechplatform.api.account.balance.models.BalanceItem
import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.transfer.api.TransferAPI
import com.fintechplatform.api.transfer.models.TransferAccountModel
import com.fintechplatform.ui.account.balance.helpers.BalancePersistence
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.FeeHelper
import com.fintechplatform.ui.money.MoneyHelper
import java.util.*

class TransferPresenter constructor(private var view: TransferContract.View,
                                    private val apiTransfer: TransferAPI,
                                    private var apiBalance: BalanceAPI,
                                    private val config: DataAccount,
                                    private var balancePersistence: BalancePersistence,
                                    private var moneyHelper: MoneyHelper,
                                    private var feeHelper: FeeHelper): TransferContract.Presenter {

    var idempotencyTransfer: String? = null
    lateinit var peerAccountModel: TransferAccountModel

    override fun initialize(p2pUserId: String, p2pAccountId: String, p2pTenantId: String, accountType:String){
        idempotencyTransfer = UUID.randomUUID().toString()

        peerAccountModel = TransferAccountModel(p2pUserId, p2pAccountId, p2pTenantId, accountType)

        refreshConfirmButton()
        refreshData()
    }

    override fun onEditingChanged() {
        refreshConfirmButton()
        refreshData()
    }

    override fun onConfirm() {
        val idemp = this.idempotencyTransfer

        idemp?.let {
            peerAccountModel?.let { p2pu ->
                view.enableForwardButton(false)
                view.showCommunicationWait()
                val money = Money.valueOf(view.getAmountText())

                apiTransfer.p2p(config.accessToken,
                        config.ownerId,
                        config.accountId,
                        config.accountType,
                        config.tenantId,
                        p2pu.userid,
                        p2pu.accountId,
                        p2pu.accountType,
                        p2pu.tenantId,
                        view.getMessageText(),
                        money.value,
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
                        return@p2p
                    }

                    view.playSound()
                    view.showSuccessDialog()
                }
            }
        }
    }

    override fun onAbortClick() {
        view.hideKeyboard()
        view.goBack()
    }

    override fun refresh() {
        view.showKeyboardAmount()

        // FIXME chiamata al server per peer full name
//        peerAccountModel?.let { p2pu ->
//            view.setPersonFullName(dbNetworkUsersHelper.getFullName(p2pu))
//        }

        reloadBalance()
    }

    fun refreshConfirmButton() {
        val amountmoney = getAmountMoney()
        val newbalance = calcBalance()

        newbalance?.let {
            view.enableForwardButton(amountmoney.value > 0 && newbalance.value >= 0)
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

            Money(it.balance.value - amountmoney.value)
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
            balancePersistence.saveBalance(BalanceItem(balance.balance, balance.availableBalance),config.accountId)

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