package com.fintechplatform.ui.profile.idcards.di

import com.fintechplatform.api.account.kyc.di.KycAPIModule
import com.fintechplatform.api.log.LogModule
import com.fintechplatform.api.net.NetModule
import com.fintechplatform.api.profile.api.IdsDocumentsAPIModule
import com.fintechplatform.api.profile.api.ProfileAPIModule
import com.fintechplatform.ui.alert.AlertHelpersModule
import com.fintechplatform.ui.images.ImageHelperModule
import com.fintechplatform.ui.profile.db.documents.DocumentsPersistanceDBModule
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule
import com.fintechplatform.ui.profile.idcards.IdentityCardsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    IdentityCardsPresenterModule::class,
    ProfileAPIModule::class,
    KycAPIModule::class,
    IdsDocumentsAPIModule::class,
    NetModule::class,
    LogModule::class,
    UsersPersistanceDBModule::class,
    DocumentsPersistanceDBModule::class,
    AlertHelpersModule::class,
    ImageHelperModule::class])
interface IdentityCardsViewComponent {
    fun inject(fragment: IdentityCardsFragment)
}
