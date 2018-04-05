package com.fintechplatform.ui.profile.address.ui;

import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.api.profile.api.ProfileAPI;
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AddressPresenterModule {

    private AddressContract.View view;
    private DataAccount dataAccount;

    public AddressPresenterModule(AddressContract.View view, DataAccount dataAccount) {
        this.view = view;
        this.dataAccount = dataAccount;
    }

    @Provides
    @Singleton
    AddressContract.Presenter providesAddressPresenter(ProfileAPI api, UsersPersistanceDB userPersistanceDB) {
        return new AddressPresenter(view, api, dataAccount, userPersistanceDB);
    }
}
