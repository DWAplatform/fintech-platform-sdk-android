package com.fintechplatform.ui.enterprise.documents.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.enterprise.db.documents.EnterpriseDocumentsPersistanceDBModule
import com.fintechplatform.ui.enterprise.documents.EnterpriseDocumentsFragment
import com.fintechplatform.ui.images.ImageHelperModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [EnterpriseDocumentsPresenterModule::class, EnterpriseAPIModule::class, NetModule::class, ImageHelperModule::class, AlertHelpersModule::class, EnterpriseDocumentsPersistanceDBModule::class, LogModule::class])
interface EnterpriseDocumentsViewComponent {
    fun inject(fragment: EnterpriseDocumentsFragment)
}
