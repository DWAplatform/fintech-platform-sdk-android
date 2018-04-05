package com.fintechplatform.android.profile.address.ui;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.profile.api.ProfileAPI;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB;

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
