package com.fintechplatform.ui.qrtransfer.qrconfirm

import android.graphics.Color
import com.fintechplatform.ui.account.balance.helpers.BalanceHelper
import com.fintechplatform.ui.account.balance.models.BalanceItem
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.money.FeeHelper
import com.fintechplatform.api.money.Money
import com.fintechplatform.ui.money.MoneyHelper
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.profile.models.UserProfile
import com.fintechplatform.api.transfer.api.TransferAPI
import com.fintechplatform.api.transfer.models.TransferAccountModel
import java.util.*

/**
 * Created by ingrid on 18/09/17.
 */
class QrConfirmPresenter constructor(var view: QrConfirmContract.View,
                                     var api: TransferAPI,
                                     val balanceHelper: BalanceHelper,
                                     var configuration: DataAccount,
                                     var moneyHelper: MoneyHelper,
                                     var feeHelper: FeeHelper,
                                     val netHelper: NetHelper): QrConfirmContract.Presenter {
    var idempotencyTransfer: String? = null
    var qrCode: String? = null
    var transferAccount: TransferAccountModel? = null
    var userProfile: UserProfile? = null


    override fun initialize(qrcode: String) {
        this.qrCode = qrcode
        idempotencyTransfer = UUID.randomUUID().toString()

        refreshBalanceData()
        refreshConfirmButton()
        showInfoElements(false)
    }


    fun showInfoElements(isVisible: Boolean) {
        view.labelPersonFullName(isVisible)
        view.userIcon(isVisible)
    }

    fun refreshBalanceData() {
        refreshBalance()
        refreshFee()
    }

    fun refreshBalance() {
        val optnewbalance = calcBalance()
        optnewbalance?.let { newbalance ->
            view.setNewBalanceAmountLabel(moneyHelper.toString(newbalance))
            view.setNewBalanceAmountColor(if (newbalance.value >= 0) Color.BLACK else Color.RED)
            refreshConfirmButton()
        }
    }


    fun getAmountMoney(): Money {
        val amount = view.getAmountText()
        val amountmoney = Money.valueOf(amount)
        return amountmoney
    }

    fun calcBalance(): Money? {
        val optbi = balanceHelper.persistence.getBalanceItem(configuration.accountId)
        val optnewbalance = optbi?.let {
            val amountmoney = getAmountMoney()

            Money(optbi.money.value - amountmoney.value)
        }
        return optnewbalance
    }

    fun refreshFee() {
        val amountmoney = getAmountMoney()

        val moneyFee =
                if (amountmoney.value == 0L) {
                    Money(0)
                } else {
                    Money(feeHelper.calcPayOutFee(amountmoney.value))
                }

        view.setFeeAmount(moneyHelper.toString(moneyFee))
    }


    override fun onCancel() {
        sendConfirmStateAndExit(false)
    }

    override fun onConfirm() {
        calcBalance()?.let { balance ->
            if (balance.value >= 0)
                sendConfirmStateAndExit(true)
            else
                view.goToPayIn(balance.value * -1)
        }
    }

    fun sendConfirmStateAndExit(confirmed: Boolean) {
        val idemp = this.idempotencyTransfer ?: return
        val transferAccount = this.transferAccount ?: return
        val amount = transferAccount.money ?: return
        view.setForwardButtonEnable(false)
        view.showWaitingSpin()
        view.hideKeyboard()

        api.p2p(configuration.accessToken,
                configuration.ownerId,
                configuration.accountId,
                configuration.accountType,
                configuration.tenantId,
                transferAccount.userid,
                transferAccount.accountId,
                transferAccount.accountType,
                transferAccount.tenantId,
                transferAccount.message,
                amount.value,
                idemp) { opterror ->

            view.hideWaitingSpin()
            refreshConfirmButton()

            if (opterror != null) {
                when (opterror) {
                    is NetHelper.IdempotencyError ->
                        view.showIdempotencyError()
                    is NetHelper.TokenError ->
                            view.showTokenError()
                    else ->
                        view.showInternalError()
                }
                return@p2p
            }

            if (confirmed) {
                view.playSound()
                view.showSuccessComunication()
            } else {
                view.goBack()
            }
        }
    }

    fun refreshConfirmButton() {
        val amountmoney = getAmountMoney()
        val newbalance = calcBalance()

        newbalance?.let {
            view.setForwardButtonEnable(amountmoney.value > 0)

            if(newbalance.value < 0 ){
                view.setForwardButtonPayIn()
            } else {
                view.setForwardTextConfirm()
            }
        }
    }

    override fun onRefresh() {
        view.refreshAmountData()
        fetchData()
        reloadBalance()
    }

    fun fetchData() {
        showInfoElements(false)
        val qrCode = this.qrCode ?: return

        view.setForwardButtonEnable(false)
        view.showWaitingSpin()

        api.getQr(configuration.accessToken, qrCode, configuration.tenantId) { optqrcoderequest, opterror ->

            view.hideWaitingSpin()

            if (opterror != null) {
                view.showQrCodeError()
                return@getQr
            }

            if (optqrcoderequest == null) {
                view.showInternalError()
                return@getQr
            }
            transferAccount = optqrcoderequest

            userProfile = UserProfile(transferAccount?.userid ?: "",
                    name = transferAccount?.name,
                    photo = transferAccount?.photo)

            refreshInfoElements()

        }

    }

    fun refreshInfoElements() {
        val qrcoderequest = this.transferAccount ?: return

        view.setLabelPersonFullName(qrcoderequest.name?:"")

        val mhString = moneyHelper.toStringNoCurrency(qrcoderequest.money?:return)
        view.setAmountText(mhString)

        view.setUserImage(userProfile?.photo)

        view.setForwardButtonEnable(true)

        showInfoElements(true)
        refreshBalanceData()
    }


    fun reloadBalance() {
        balanceHelper.api.balance(configuration.accessToken, configuration.ownerId, configuration.accountId, configuration.accountType, configuration.tenantId) { optbalance, opterror ->
            if (opterror != null) {
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

    private fun handleErrors(error: Exception) {
        when (error) {
            is NetHelper.TokenError ->
                view.showTokenError()
            else ->
                view.showInternalError()
        }
    }
}