package com.dwaplatform.android.auth.ui

/**
 * Created by ingrid on 18/12/17.
 */
interface AuthenticationContract {
    interface View {
        fun requestFocus()
        fun getPinEntry(): String
        fun setTokenUser(token: String)
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