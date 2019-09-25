package com.fintechplatform.ui.enterprise.address.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.enterprise.address.EnterpriseAddressFragment
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDBModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [EnterpriseAddressPresenterModule::class, EnterpriseAPIModule::class, IdsDocumentsAPIModule::class, NetModule::class, LogModule::class, AlertHelpersModule::class, EnterprisePersistanceDBModule::class])
interface EnterpriseAddressViewComponent {
    fun inject(fragment: EnterpriseAddressFragment)
}
