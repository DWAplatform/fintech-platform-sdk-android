package com.fintechplatform.ui.iban.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.api.iban.api.IbanAPI
import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB
import com.fintechplatform.ui.iban.IBANContract
import com.fintechplatform.ui.iban.IBANPresenter
import com.fintechplatform.ui.iban.db.IbanPersistanceDB
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class IBANPresenterModule(private val view: IBANContract.View, private val configuration: DataAccount) {

    @Singleton
    @Provides
    internal fun providesIBanPresenter(api: IbanAPI,
                                       profileAPI: ProfileAPI,
                                       enterpriseAPI: EnterpriseAPI,
                                       ibanPersistanceDB: IbanPersistanceDB,
                                       usersPersistanceDB: UsersPersistanceDB,
                                       enterprisePersistanceDB: EnterprisePersistanceDB): IBANContract.Presenter = 
            IBANPresenter(view, api, profileAPI, enterpriseAPI, configuration, ibanPersistanceDB, usersPersistanceDB, enterprisePersistanceDB)
}