package com.dwaplatform.android.auth.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.dwaplatform.android.R
import com.dwaplatform.android.alert.AlertHelpers
import com.dwaplatform.android.keys.CheckPinState
import com.dwaplatform.android.keys.KeyChain
import com.dwaplatform.android.models.SendEmailHelper
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

/**
 * Requests to insert the PIN before access the main activity
 */
class AuthenticationActivity: FragmentActivity(), AuthenticationContract.View {

    @Inject lateinit var presenter: AuthenticationContract.Presenter
    //@Inject lateinit var api: DWApayAPI
    @Inject lateinit var alertHelpers: AlertHelpers
    //@Inject lateinit var dbhelper: DBUsersHelper
    @Inject lateinit var keyChain: KeyChain
    @Inject lateinit var emailHelper: SendEmailHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(application as App).netComponent?.inject(this)
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

    override fun goToMain() {
//        startActivity(Intent(this, MainActivity::class.java))
//        finish()
    }

    override fun setTokenUser(token: String) {
        //todo sharedpreferences need application context?
        keyChain.set("tokenuser", token, this)
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