package com.fintechplatform.ui.profile.idcards.di

import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.ui.images.ImageHelper
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.db.documents.DocumentsPersistanceDB
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB
import com.fintechplatform.ui.profile.idcards.IdentityCardsContract
import com.fintechplatform.ui.profile.idcards.IdentityCardsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class IdentityCardsPresenterModule(private val view: IdentityCardsContract.View, private val configuration: DataAccount) {

    @Provides
    @Singleton
    internal fun providesIdentityCardsPresenter(api: ProfileAPI,
                                                documents: DocumentsPersistanceDB,
                                                users: UsersPersistanceDB,
                                                imageHelper: ImageHelper): IdentityCardsContract.Presenter {
        return IdentityCardsPresenter(view, api, configuration, documents, users, imageHelper)
    }
}
