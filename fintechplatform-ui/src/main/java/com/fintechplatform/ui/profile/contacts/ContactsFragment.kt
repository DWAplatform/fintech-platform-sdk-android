package com.fintechplatform.ui.profile.contacts

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.fintechplatform.ui.R
import com.fintechplatform.ui.alert.AlertHelpers
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.contacts.di.ContactsViewComponent
import kotlinx.android.synthetic.main.activity_profile_contacts.*
import kotlinx.android.synthetic.main.activity_profile_contacts.view.*
import javax.inject.Inject


open class ContactsFragment: Fragment(), ContactsContract.View {

    @Inject
    lateinit var presenter: ContactsContract.Presenter
    @Inject
    lateinit var alertHelper: AlertHelpers

    var navigation: ContactsContract.Navigation? = null

    open fun createContactsComponent(context: Context, view: ContactsContract.View, hostName: String, dataAccount: DataAccount): ContactsViewComponent {
        return ContactsUI.Builder.buildContactsViewComponet(context, view, hostName, dataAccount)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_profile_contacts, container, false)

        arguments?.getString("hostname")?.let { hostName ->
            arguments?.getParcelable<DataAccount>("dataAccount")?.let { dataAccount ->
                createContactsComponent(context, this, hostName, dataAccount).inject(this)
            }
        }

        view.emailText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.refreshConfirmButton()
            }
        })

        view.telephoneText.keyListener = null

        view.backwardButton.setOnClickListener { presenter.onAbort() }

        view.confirmButton.setOnClickListener { presenter.onConfirm() }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.initializate()
    }



    override fun onResume() {
        super.onResume()
        presenter.onRefresh()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (context as? ContactsContract.Navigation)?.let {
            navigation = it
        }?: Log.e(ContactsFragment::class.java.canonicalName, "ContactsContract.Navigation must be implemented in your Activity!!")
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
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
        alertHelper.tokenExpired(context) { _,_ ->
            navigation?.backFromContacts()
        }
    }

    override fun showEmailError() {
        alertHelper.genericError(context, "Riprovare", "email non valida").show()
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
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun goBack() {
        navigation?.backFromContacts()
    }

    companion object {
        fun newInstance(hostname: String, dataAccount: DataAccount): ContactsFragment {
            val frag = ContactsFragment()
            val args = Bundle()
            args.putString("hostname", hostname)
            args.putParcelable("dataAccount", dataAccount)
            frag.arguments = args
            return frag
        }
    }
}