package com.fintechplatform.ui.profile.contacts.di

import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.contacts.ContactsContract
import com.fintechplatform.ui.profile.contacts.ContactsPresenter
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContactsPresenterModule(private val view: ContactsContract.View, private val configuration: DataAccount) {

    @Provides
    @Singleton
    internal fun providesContactsPresenter(api: ProfileAPI, usersPersistanceDB: UsersPersistanceDB): ContactsContract.Presenter {
        return ContactsPresenter(view, api, configuration, usersPersistanceDB)

    }
}
