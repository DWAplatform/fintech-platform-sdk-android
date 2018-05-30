package com.fintechplatform.ui.secure3d.ui

import javax.inject.Inject

open class Secure3DPresenter @Inject constructor(val view: Secure3DContract.View): Secure3DContract.Presenter {

    override fun initialize(url: String) {
        view.loadUrl(url)
    }

    override fun onPageFinished(url: String) {
        view.hideCommunicationWait()
        if (url.contains("mangopay3dsecureCallbackHandler?transactionId")) {
            view.goBack()
        }
    }

    override fun onPageStarted() {
        view.showCommunicationWait()
    }
}