package com.fintechplatform.android.secure3d.ui

/**
 * Created by ingrid on 12/12/17.
 */
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
}