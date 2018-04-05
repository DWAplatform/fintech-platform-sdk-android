package com.fintechplatform.api.sample.auth.ui

/**
 * Created by ingrid on 18/12/17.
 */
interface AuthenticationContract {
    interface View {
        fun requestFocus()
        fun getPinEntry(): String
        fun showWrongPinError()
        fun showMaxAttemptExpired()
        fun showWaiting()
        fun hideWaiting()
        fun showInternalError()
        fun goToMain()
    }

    interface Presenter {
        fun onEditingChanged()
    }
}