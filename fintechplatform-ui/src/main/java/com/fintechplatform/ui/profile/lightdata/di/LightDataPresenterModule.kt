package com.fintechplatform.ui.profile.lightdata.di

import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB
import com.fintechplatform.ui.profile.lightdata.LightDataContract
import com.fintechplatform.ui.profile.lightdata.LightDataPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LightDataPresenterModule(private val view: LightDataContract.View, private val configuration: DataAccount) {

    @Provides
    @Singleton
    internal fun providesLightDataPresenter(api: ProfileAPI, usersPersistanceDB: UsersPersistanceDB): LightDataContract.Presenter {
        return LightDataPresenter(view, api, configuration, usersPersistanceDB)
    }
}
