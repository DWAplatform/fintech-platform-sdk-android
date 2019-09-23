package com.fintechplatform.ui.enterprise.info.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB
import com.fintechplatform.ui.enterprise.info.EnterpriseInfoContract
import com.fintechplatform.ui.enterprise.info.EnterpriseInfoPresenter
import com.fintechplatform.ui.models.DataAccount
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EnterpriseInfoPresenterModule(private val view: EnterpriseInfoContract.View, private val configuration: DataAccount) {

    @Provides
    @Singleton
    internal fun providesLightDataPresenter(api: EnterpriseAPI, persistanceDB: EnterprisePersistanceDB): EnterpriseInfoContract.Presenter {
        return EnterpriseInfoPresenter(view, api, configuration, persistanceDB)
    }
}
