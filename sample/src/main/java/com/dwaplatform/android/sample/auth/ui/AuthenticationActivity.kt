package com.dwaplatform.android.sample.auth.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.dwaplatform.android.sample.R
import com.dwaplatform.android.alert.AlertHelpers
import com.dwaplatform.android.email.SendEmailHelper
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

/**
 * Requests to insert the PIN before access the main activity
 */
class AuthenticationActivity: FragmentActivity(), AuthenticationContract.View {

    @Inject lateinit var presenter: AuthenticationContract.Presenter
    @Inject lateinit var alertHelpers: AlertHelpers
    @Inject lateinit var emailHelper: SendEmailHelper

    val TOKEN = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthUI.createAuthViewComponent(this, this).inject(this)
        setContentView(R.layout.activity_auth)

        pinEntry.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onEditingChanged()
            }
        })
    }

    override fun getPinEntry(): String {
        return pinEntry.text.toString()
    }

    override fun requestFocus() {
        pinEntry.setText("")
        pinEntry.requestFocus()
    }

    override fun showWrongPinError() {
        alertHelpers.genericError(this,
                "PIN Errato", "Ridigitare il pin").show()
    }

    override fun goToMain(data: String) {
        finish()
    }

    override fun showMaxAttemptExpired() {
        alertHelpers.genericError(this,
                "Numero massimo tentativi superato",
                "Contattare ${emailHelper.emailsupport} per sbloccare").show()
    }

    override fun showInternalError() {
        alertHelpers.internalError(this).show()
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }
}