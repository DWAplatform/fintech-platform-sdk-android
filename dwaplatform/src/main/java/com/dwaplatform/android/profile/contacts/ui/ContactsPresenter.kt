package com.dwaplatform.android.profile.contacts.ui

import com.dwaplatform.android.auth.keys.KeyChain
import com.dwaplatform.android.email.EmailHelper
import com.dwaplatform.android.models.DataAccount
import com.dwaplatform.android.profile.api.ProfileAPI
import com.dwaplatform.android.profile.db.UsersPersistanceDB
import com.dwaplatform.android.profile.models.UserContacts
import com.dwaplatform.android.profile.models.UserProfileReply
import javax.inject.Inject

class ContactsPresenter @Inject constructor(val view: ContactsContract.View,
                                            val api: ProfileAPI,
                                            val configuration: DataAccount,
                                            val keyChain: KeyChain,
                                            val usersPersistanceDB: UsersPersistanceDB): ContactsContract.Presenter {

    override fun initializate() {
        val userProfile = usersPersistanceDB.userProfile()
        userProfile?.let {
            view.setEmailText(it.email?: "")
            view.setTelephoneText(it.telephone?: "")
        }
    }

    override fun onRefresh() {
        view.enableAllTexts(false)

        api.searchUser(keyChain["tokenuser"],
                usersPersistanceDB.userid()){ profile, exception ->

            if (exception != null){
                return@searchUser
            }

            if(profile == null){
                return@searchUser
            }

            view.enableAllTexts(true)

            profile.email?.let {
                val usercontacts = UserContacts(
                        configuration.userId,
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

            val contacts = UserContacts(configuration.userId, view.getEmailText(), view.getTelephoneText())
            api.contacts(keyChain["tokenuser"],
                    contacts) { profileReply: UserProfileReply?, exception: Exception? ->
                if (exception != null) {
                    view.hideWaiting()
                    view.showCommunicationInternalNetwork()
                    return@contacts
                }

                if (profileReply == null) {
                    view.hideWaiting()
                    view.showCommunicationInternalNetwork()
                    return@contacts
                }

                val userProfile = UserContacts(
                        configuration.userId,
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

    override fun refreshConfirmButton() {
        if(view.getEmailText().isNotBlank()){
            view.setAbortText()
            view.enableConfirmButton(true)
        } else {
            view.enableConfirmButton(false)
        }
    }
}