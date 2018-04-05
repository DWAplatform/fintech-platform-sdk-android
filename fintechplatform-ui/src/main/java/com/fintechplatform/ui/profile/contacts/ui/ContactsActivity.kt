package com.fintechplatform.ui.profile.contacts.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import kotlinx.android.synthetic.main.activity_profile_contacts.*
import javax.inject.Inject

/**
 * Shows email and telephone contacts
 */
class ContactsActivity: AppCompatActivity(), ContactsContract.View {

    @Inject lateinit var presenter: ContactsContract.Presenter
    @Inject lateinit var alertHelper: AlertHelpers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContactsUI.instance.createContactsComponent(this as Context, this).inject(this)
        setContentView(R.layout.activity_profile_contacts)

        emailText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        telephoneText.keyListener = null

        backwardButton.setOnClickListener { presenter.onAbort() }

        confirmButton.setOnClickListener { presenter.onConfirm() }

        presenter.initializate()
    }

    override fun onBackPressed() {
        presenter.onAbort()
    }

    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun setBackwardText() {
        backwardButton.text = resources.getString(R.string.navheader_back)
    }

    override fun setAbortText() {
        backwardButton.text = resources.getString(R.string.abort)
    }

    override fun getEmailText(): String {
        return emailText.text.toString()
    }

    override fun getTelephoneText(): String {
        return telephoneText.text.toString()
    }

    override fun setEmailText(email: String) {
        emailText.setText(email)
    }

    override fun setTelephoneText(phone: String) {
        telephoneText.setText(phone)
    }

    override fun enableAllTexts(isEnable: Boolean) {
        emailText.isEnabled = isEnable
        telephoneText.isEnabled = isEnable
    }

    override fun showTokenExpiredWarning() {
        alertHelper.tokenExpired(this, { _,_ ->
            finish()
        })
    }

    override fun showEmailError() {
        alertHelper.genericError(this, "Riprovare", "email non valida").show()
    }

    override fun enableConfirmButton(isEnable: Boolean) {
        confirmButton.isEnabled = isEnable
    }

    override fun showWaiting() {
        activityIndicator.visibility = View.VISIBLE
    }

    override fun hideWaiting() {
        activityIndicator.visibility = View.GONE
    }

    override fun hideKeyboard() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun goBack() {
        finish()
        overridePendingTransition(R.anim.back_enter, R.anim.back_leave)
    }
}