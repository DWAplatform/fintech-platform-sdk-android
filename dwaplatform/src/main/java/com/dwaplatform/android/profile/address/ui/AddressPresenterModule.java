package com.dwaplatform.android.profile.address.ui;

import com.dwaplatform.android.auth.keys.KeyChain;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.api.ProfileAPI;
import com.dwaplatform.android.profile.db.UsersPersistanceDB;

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
    AddressContract.Presenter providesAddressPresenter(ProfileAPI api, KeyChain keyChain, UsersPersistanceDB userPersistanceDB) {
        return new AddressPresenter(view, api, keyChain, dataAccount, userPersistanceDB);
    }
}
