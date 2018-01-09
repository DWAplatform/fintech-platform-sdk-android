package com.dwaplatform.android.auth.ui

import android.content.Intent
import com.dwaplatform.android.auth.api.AuthenticationAPI
import com.dwaplatform.android.auth.keys.CheckPinState
import com.dwaplatform.android.auth.keys.KeyChain
import com.dwaplatform.android.log.Log

/**
 * Created by ingrid on 18/12/17.
 */
class AuthenticationPresenter constructor(val view: AuthenticationContract.View,
                                          val log: Log,
                                          val api: AuthenticationAPI,
                                          val userid: String,
                                          val keyChain: KeyChain
): AuthenticationContract.Presenter {
    override fun onEditingChanged() {
        val pin = view.getPinEntry()
        if (pin.length != 5)
            return

        view.showWaiting()

        api.checkpin(userid, pin) { optCheckPin, opterror ->

            view.hideWaiting()

            if (opterror != null) {
                view.showInternalError()
                return@checkpin
            }

            if (optCheckPin == null) {
                view.showInternalError()
                return@checkpin
            }
            val checkPin = optCheckPin

            when(checkPin.status) {
                CheckPinState.CODE_ERROR -> {
                    view.requestFocus()
                    view.showWrongPinError()
                }
                CheckPinState.LIMIT_REACHED -> {
                    view.requestFocus()
                    view.showMaxAttemptExpired()
                }
                CheckPinState.SUCCESS -> {
                    keyChain["tokenuser"] = checkPin.token
                    view.goToMain()
                }
            }
        }
    }

}