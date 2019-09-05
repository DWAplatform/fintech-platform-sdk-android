package com.fintechplatform.ui.secure3d

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

    interface Navigation {
        fun goBackwardFrom3dSecure()
    }
}