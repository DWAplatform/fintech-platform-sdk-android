package com.fintechplatform.ui.profile.address.di

import com.fintechplatform.api.profile.api.ProfileAPI
import com.fintechplatform.ui.models.DataAccount
import com.fintechplatform.ui.profile.address.AddressContract
import com.fintechplatform.ui.profile.address.AddressPresenter
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AddressPresenterModule(private val view: AddressContract.View, private val dataAccount: DataAccount) {

    @Provides
    @Singleton
    internal fun providesAddressPresenter(api: ProfileAPI, userPersistanceDB: UsersPersistanceDB): AddressContract.Presenter {
        return AddressPresenter(view, api, dataAccount, userPersistanceDB)
    }
}