package com.fintechplatform.ui.profile.contacts.di

import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.profile.contacts.ContactsFragment
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ContactsPresenterModule::class, 
    AlertHelpersModule::class, 
    ProfileAPIModule::class, 
    IdsDocumentsAPIModule::class,
    NetModule::class, 
    UsersPersistanceDBModule::class,
    LogModule::class
])
interface ContactsViewComponent {
    fun inject(fragment: ContactsFragment)
}