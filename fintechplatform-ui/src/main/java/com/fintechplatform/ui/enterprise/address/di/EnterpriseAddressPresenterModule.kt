package com.fintechplatform.ui.enterprise.address.di

import com.fintechplatform.api.enterprise.api.EnterpriseAPI
import com.fintechplatform.ui.enterprise.address.EnterpriseAddressContract
import com.fintechplatform.ui.enterprise.address.EnterpriseAddressPresenter
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB
import com.fintechplatform.ui.models.DataAccount
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class EnterpriseAddressPresenterModule(private val view: EnterpriseAddressContract.View, private val dataAccount: DataAccount) {

    @Provides
    @Singleton
    internal fun providesAddressPresenter(api: EnterpriseAPI, persistanceDB: EnterprisePersistanceDB): EnterpriseAddressContract.Presenter {
        return EnterpriseAddressPresenter(view, api, dataAccount, persistanceDB)
    }
}
