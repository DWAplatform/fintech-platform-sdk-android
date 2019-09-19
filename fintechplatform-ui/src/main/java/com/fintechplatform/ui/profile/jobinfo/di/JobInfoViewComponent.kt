package com.fintechplatform.ui.profile.jobinfo.di

import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule
import com.fintechplatform.ui.profile.jobinfo.JobInfoFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    JobInfoPresenterModule::class, 
    NetModule::class, 
    ProfileAPIModule::class, 
    IdsDocumentsAPIModule::class,
    LogModule::class, 
    AlertHelpersModule::class, 
    UsersPersistanceDBModule::class
])
interface JobInfoViewComponent {
    fun inject(fragment: JobInfoFragment)
}
