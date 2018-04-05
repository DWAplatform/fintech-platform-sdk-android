package com.fintechplatform.api.sample.auth.ui

import com.fintechplatform.api.log.Log
import com.fintechplatform.api.sample.auth.api.AuthenticationAPI
import com.fintechplatform.api.sample.auth.keys.CheckPinState
import com.fintechplatform.api.sample.auth.keys.KeyChain

/**
 * Created by ingrid on 18/12/17.
 */
class AuthenticationPresenter constructor(val view: AuthenticationContract.View,
                                          val log: Log,
                                          val api: AuthenticationAPI,
                                          val userid: String,
                                          val tenantId: String,
                                          val keyChain: KeyChain
): AuthenticationContract.Presenter {
    override fun onEditingChanged() {
        val pin = view.getPinEntry()
        if (pin.length != 5)
            return

        view.showWaiting()

        api.userToken(userid, tenantId, pin) { optCheckPin, opterror ->

            view.hideWaiting()

            if (opterror != null) {
                view.showInternalError()
                return@userToken
            }

            if (optCheckPin == null) {
                view.showInternalError()
                return@userToken
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
                    keyChain["accessToken"] = checkPin.token
                    view.goToMain()
                }
            }
        }
    }

}