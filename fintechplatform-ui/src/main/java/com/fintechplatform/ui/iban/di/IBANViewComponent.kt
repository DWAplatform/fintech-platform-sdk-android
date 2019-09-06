package com.fintechplatform.ui.iban.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.iban.api.IbanAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDBModule
import com.fintechplatform.ui.iban.IBANFragment
import com.fintechplatform.ui.iban.db.IbanPersistanceDBModule
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    IBANPresenterModule::class,
    IbanAPIModule::class,
    AlertHelpersModule::class,
    ProfileAPIModule::class,
    EnterpriseAPIModule::class,
    NetModule::class,
    LogModule::class,
    UsersPersistanceDBModule::class,
    EnterprisePersistanceDBModule::class,
    IbanPersistanceDBModule::class])
interface IBANViewComponent {
    fun inject(fragment: IBANFragment)
}