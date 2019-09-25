package com.fintechplatform.ui.enterprise.contacts.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.enterprise.contacts.EnterpriseContactsFragment
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDBModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [EnterpriseContactsPresenterModule::class, AlertHelpersModule::class, EnterpriseAPIModule::class, IdsDocumentsAPIModule::class, NetModule::class, EnterprisePersistanceDBModule::class, LogModule::class])
interface EnterpriseContactsViewComponent {
    fun inject(fragment: EnterpriseContactsFragment)
}
