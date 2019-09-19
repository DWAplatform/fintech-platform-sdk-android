package com.fintechplatform.ui.profile.jobinfo.di

import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB
import com.fintechplatform.ui.profile.jobinfo.JobInfoContract
import com.fintechplatform.ui.profile.jobinfo.JobInfoPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class JobInfoPresenterModule(private val view: JobInfoContract.View, private val configuration: DataAccount) {

    @Provides
    @Singleton
    internal fun providesJobInfoPresenter(api: ProfileAPI, usersPersistanceDB: UsersPersistanceDB): JobInfoContract.Presenter {
        return JobInfoPresenter(view, api, configuration, usersPersistanceDB)
    }
}