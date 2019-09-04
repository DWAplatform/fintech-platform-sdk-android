package com.fintechplatform.ui.qrtransfer.creditui

import com.fintechplatform.api.money.Money
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.api.transfer.api.TransferAPI
import com.fintechplatform.ui.models.DataAccount
import java.util.*

/**
 * Created by ingrid on 15/09/17.
 */
class QrReceiveAmountFragmentPresenter constructor(var view: QrReceiveAmountContract.View,
                                                   var api: TransferAPI,
                                                   var dataAccount: DataAccount): QrReceiveAmountContract.Presenter {

    interface QrReceiveFragmentListener {
        fun onQrReceiveAbort()
        fun onQrReceiveTransactionId(transactionId: String)
    }

    private var listener: QrReceiveFragmentListener? = null

    override fun setListener(listener: QrReceiveFragmentListener) {
        this.listener = listener
    }

    override fun removeListener() {
        listener = null
    }

    override fun refreshConfirmButton() {
        view.setForwardButtonEnable(view.getAmountTextLength() > 0)
    }

    override fun onConfirm() {

        view.setForwardButtonEnable(false)
        view.showCommunicationWait()
        val idempotency = UUID.randomUUID().toString()

        val money = Money.valueOf(view.getAmountText())
        api.qrCredit(dataAccount.accessToken,
                dataAccount.tenantId,
                dataAccount.ownerId,
                dataAccount.accountId,
                dataAccount.accountType,
                view.getMessagetext(),
                money.value,
                idempotency) { optQrtransferId, exception ->
            if(exception != null) {
                handleError(exception)
                return@qrCredit
            }

            if(optQrtransferId == null) {
                return@qrCredit
            }
            view.hideKeyboard()
            listener?.onQrReceiveTransactionId(optQrtransferId)
        }
    }

    override fun onAbortClick() {
        listener?.onQrReceiveAbort()
    }

    private fun handleError(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                view.showCommunicationInternalError()
        }
    }
}