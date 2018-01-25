package com.fintechplatform.android.enterprise.contacts.ui

import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.email.EmailHelper
import com.fintechplatform.android.enterprise.api.EnterpriseAPI
import com.fintechplatform.android.enterprise.db.enterprise.EnterprisePersistanceDB
import com.fintechplatform.android.enterprise.models.EnterpriseContacts
import com.fintechplatform.android.models.DataAccount
import javax.inject.Inject

class EnterpriseContactsPresenter @Inject constructor(val view: EnterpriseContactsContract.View,
                                                      val api: EnterpriseAPI,
                                                      val configuration: DataAccount,
                                                      val persistanceDB: EnterprisePersistanceDB): EnterpriseContactsContract.Presenter {

    var token:String?=null

    override fun initializate() {
        val userProfile = persistanceDB.enterpriseProfile(configuration.accountId)
        userProfile?.let {
            view.setEmailText(it.email?: "")
            view.setTelephoneText(it.telephone?: "")
        }
    }

    override fun onRefresh() {
        view.enableAllTexts(false)

        api.getEnterprise(configuration.accessToken,
                configuration.accountId){ enterprise, exception ->

            if (exception != null){
                handlerErrors(exception)
                return@getEnterprise
            }

            if(enterprise == null){
                return@getEnterprise
            }

            view.enableAllTexts(true)

            enterprise.email?.let {
                val enterprisecontacts = EnterpriseContacts(
                        configuration.accountId,
                        enterprise.email,
                        enterprise.telephone
                )

                persistanceDB.saveContacts(enterprisecontacts)
            }

            initializate()
            view.setBackwardText()
            view.enableConfirmButton(false)
        }
    }

    override fun onAbort() {
        view.hideKeyboard()
        view.goBack()
    }

    override fun onConfirm() {
        view.showWaiting()
        view.hideKeyboard()

        if (EmailHelper().validate(view.getEmailText()) &&
                view.getTelephoneText().length >= 12 ) {

            view.hideKeyboard()

            val contacts = EnterpriseContacts(configuration.accountId, view.getEmailText(), view.getTelephoneText())
            api.contacts(configuration.accessToken,
                    contacts) { enterpriseReply, exception ->

                if (exception != null) {
                    view.hideWaiting()
                    handlerErrors(exception)
                    return@contacts
                }

                if (enterpriseReply == null) {
                    view.hideWaiting()
                    return@contacts
                }

                val enterpriseContacts = EnterpriseContacts(
                        configuration.accountId,
                        view.getEmailText(),
                        view.getTelephoneText())

                view.hideWaiting()
                view.enableConfirmButton(false)
                persistanceDB.saveContacts(enterpriseContacts)
                view.goBack()
            }
        } else {
            view.hideWaiting()
            view.showEmailError()
        }
    }

    private fun handlerErrors(opterror: Exception) {
        when (opterror) {
            is NetHelper.TokenError ->
                view.showTokenExpiredWarning()
            else ->
                return
        }
    }

    override fun refreshConfirmButton() {
        if(view.getEmailText().isNotBlank()){
            view.setAbortText()
            view.enableConfirmButton(true)
        } else {
            view.enableConfirmButton(false)
        }
    }
}