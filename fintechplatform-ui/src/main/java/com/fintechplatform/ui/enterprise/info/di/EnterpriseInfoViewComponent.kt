package com.fintechplatform.ui.enterprise.info.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDBModule
import com.fintechplatform.ui.enterprise.info.EnterpriseInfoFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [EnterpriseInfoPresenterModule::class, EnterpriseAPIModule::class, NetModule::class, LogModule::class, AlertHelpersModule::class, EnterprisePersistanceDBModule::class])
interface EnterpriseInfoViewComponent {
    fun inject(fragment: EnterpriseInfoFragment)
}
