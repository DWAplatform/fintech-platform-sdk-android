package com.fintechplatform.android.transfer.ui

import com.fintechplatform.android.account.balance.api.BalanceAPI
import com.fintechplatform.android.account.balance.helpers.BalancePersistence
import com.fintechplatform.android.account.balance.models.BalanceItem
import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.money.FeeHelper
import com.fintechplatform.android.money.Money
import com.fintechplatform.android.money.MoneyHelper
import com.fintechplatform.android.transfer.api.TransferAPI
import com.fintechplatform.android.transfer.models.PeersModel
import java.util.*

class TransferPresenter constructor(var view: TransferContract.View,
                                    val apiTransfer: TransferAPI,
                                    var apiBalance: BalanceAPI,
                                    val config: DataAccount,
                                    var balancePersistence: BalancePersistence,
                                    var moneyHelper: MoneyHelper,
                                    var feeHelper: FeeHelper): TransferContract.Presenter {

    var idempotencyTransfer: String? = null
    lateinit var networkUserModel: PeersModel

    override fun initialize(p2pUserId: String, p2pAccountId: String, p2pTenantId: String){
        idempotencyTransfer = UUID.randomUUID().toString()

        // TODO take info about user network from server restCall(Method.GET,  "/rest/v1/mobile/tenants/:tenantId/users/:userId", findUserFromId _),
        //networkUserModel = dbNetworkUsersHelper.findP2P(p2pUserId)
        networkUserModel = PeersModel(p2pUserId, p2pAccountId, p2pTenantId)

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
            networkUserModel?.let { p2pu ->
                view.enableForwardButton(false)
                view.showCommunicationWait()
                val money = Money.valueOf(view.getAmountText())

                apiTransfer.p2p(config.accessToken,
                        config.userId,
                        config.accountId,
                        config.tenantId,
                        p2pu.userid,
                        p2pu.accountId,
                        p2pu.tenantId,
                        view.getMessageText(),
                        money.value) { opterror ->

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

//        networkUserModel?.let { p2pu ->
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
        apiBalance.balance(config.accessToken, config.userId, config.accountId, config.tenantId) { optbalance, opterror ->
            if (opterror != null) {
                handleErrors(opterror)
                return@balance
            }

            if (optbalance == null) {
                return@balance
            }
            val balance = optbalance
            balancePersistence.saveBalance(BalanceItem(config.accountId, balance))

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