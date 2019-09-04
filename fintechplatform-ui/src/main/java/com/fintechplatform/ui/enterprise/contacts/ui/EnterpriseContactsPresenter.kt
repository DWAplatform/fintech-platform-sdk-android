package com.fintechplatform.ui.enterprise.contacts.ui

import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.api.enterprise.models.EnterpriseContacts
import com.fintechplatform.api.net.NetHelper
import com.fintechplatform.ui.email.EmailHelper
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB
import com.fintechplatform.ui.models.DataAccount
import javax.inject.Inject

class EnterpriseContactsPresenter @Inject constructor(val view: EnterpriseContactsContract.View,
                                                      val api: EnterpriseAPI,
                                                      val configuration: DataAccount,
                                                      val persistanceDB: EnterprisePersistanceDB): EnterpriseContactsContract.Presenter {

    var token:String?=null

    override fun initializate() {
        val userProfile = persistanceDB.enterpriseProfile(configuration.ownerId)
        userProfile?.let {
            view.setEmailText(it.email?: "")
            view.setTelephoneText(it.telephone?: "")
        }
    }

    override fun onRefresh() {
        view.enableAllTexts(false)

        api.getEnterprise(configuration.accessToken,
                configuration.ownerId,
                configuration.tenantId){ enterprise, exception ->

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
                        configuration.ownerId,
                        configuration.accountId,
                        configuration.tenantId,
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

        if (EmailHelper().validate(view.getEmailText())) {

            view.hideKeyboard()

            val contacts = EnterpriseContacts(configuration.ownerId, configuration.accountId, configuration.tenantId, view.getEmailText(), view.getTelephoneText())
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
                        configuration.ownerId,
                        configuration.accountId,
                        configuration.tenantId,
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
        if(view.getEmailText().isNotBlank() &&
                view.getTelephoneText().isNotBlank()){
            view.setAbortText()
            view.enableConfirmButton(true)
        } else {
            view.enableConfirmButton(false)
        }
    }
}