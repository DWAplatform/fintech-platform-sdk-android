package com.fintechplatform.ui.secure3d.ui

interface Secure3DContract {

    interface View {
        fun showCommunicationWait()

        fun hideCommunicationWait()

        fun loadUrl(url: String)

        fun goBack()
    }

    interface Presenter {
        fun initialize(url: String)

        fun onPageStarted()

        fun onPageFinished(url: String)
    }

    interface Navigator {
        fun goBackwardFrom3dSecure()
    }
}