package com.fintechplatform.android.qrtransfer.creditui

/**
 * Created by ingrid on 18/09/17.
 */
class QrReceiveActivityPresenter constructor(var view: QrReceiveActivityContract.View):
        QrReceiveActivityContract.Presenter, QrReceiveAmountFragmentPresenter.QrReceiveFragmentListener {

    override fun createAmountFragment() {
        view.showAmountFragment()
    }

    override fun onQrReceiveAbort() {
        view.goBack()
    }

    override fun onQrReceiveTransactionId(transactionid: String) {
        view.showShowFragmentWithTransactionId(transactionid)
    }
}