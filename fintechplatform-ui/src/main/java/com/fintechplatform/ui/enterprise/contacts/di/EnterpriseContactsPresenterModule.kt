package com.fintechplatform.ui.enterprise.contacts.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.ui.enterprise.contacts.EnterpriseContactsContract
import com.fintechplatform.ui.enterprise.contacts.EnterpriseContactsPresenter
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB
import com.fintechplatform.ui.models.DataAccount
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class EnterpriseContactsPresenterModule(private val view: EnterpriseContactsContract.View, private val configuration: DataAccount) {

    @Provides
    @Singleton
    internal fun providesContactsPresenter(api: EnterpriseAPI, persistanceDB: EnterprisePersistanceDB): EnterpriseContactsContract.Presenter {
        return EnterpriseContactsPresenter(view, api, configuration, persistanceDB)

    }
}