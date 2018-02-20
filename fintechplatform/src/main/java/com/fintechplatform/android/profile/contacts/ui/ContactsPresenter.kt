package com.fintechplatform.android.profile.contacts.ui

import com.fintechplatform.android.api.NetHelper
import com.fintechplatform.android.email.EmailHelper
import com.fintechplatform.android.models.DataAccount
import com.fintechplatform.android.profile.api.ProfileAPI
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB
import com.fintechplatform.android.profile.models.UserContacts
import com.fintechplatform.android.profile.models.UserProfileReply
import javax.inject.Inject

class ContactsPresenter @Inject constructor(val view: ContactsContract.View,
                                            val api: ProfileAPI,
                                            val configuration: DataAccount,
                                            val usersPersistanceDB: UsersPersistanceDB): ContactsContract.Presenter {

    var token:String?=null

    override fun initializate() {
        val userProfile = usersPersistanceDB.userProfile(configuration.ownerId)
        userProfile?.let {
            view.setEmailText(it.email?: "")
            view.setTelephoneText(it.telephone?: "")
        }
    }

    override fun onRefresh() {
        view.enableAllTexts(false)

        api.searchUser(configuration.accessToken,
                configuration.ownerId, configuration.tenantId){ profile, exception ->

            if (exception != null){
                handlerErrors(exception)
                return@searchUser
            }

            if(profile == null){
                return@searchUser
            }

            view.enableAllTexts(true)

            profile.email?.let {
                val usercontacts = UserContacts(
                        configuration.ownerId,
                        configuration.tenantId,
                        profile.email,
                        profile.telephone
                )

                usersPersistanceDB.saveContacts(usercontacts)
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

            val contacts = UserContacts(configuration.ownerId, configuration.tenantId, view.getEmailText(), view.getTelephoneText())
            api.contacts(configuration.accessToken,
                    contacts) { profileReply: UserProfileReply?, exception: Exception? ->

                if (exception != null) {
                    view.hideWaiting()
                    handlerErrors(exception)
                    return@contacts
                }

                if (profileReply == null) {
                    view.hideWaiting()
                    return@contacts
                }

                val userProfile = UserContacts(
                        configuration.ownerId,
                        configuration.tenantId,
                        view.getEmailText(),
                        view.getTelephoneText())

                view.hideWaiting()
                view.enableConfirmButton(false)
                usersPersistanceDB.saveContacts(userProfile)
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