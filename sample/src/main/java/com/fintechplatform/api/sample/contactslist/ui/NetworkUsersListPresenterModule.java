package com.fintechplatform.api.sample.contactslist.ui;

import com.fintechplatform.api.models.DataAccount;
import com.fintechplatform.api.sample.contactslist.api.PeersAPI;
import com.fintechplatform.api.sample.contactslist.db.NetworkUsersPersistance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkUsersListPresenterModule {

    private NetworkUsersListContract.View view;
    private DataAccount dataAccount;

    public NetworkUsersListPresenterModule(NetworkUsersListContract.View view, DataAccount dataAccount) {
        this.view = view;
        this.dataAccount = dataAccount;
    }

    @Provides
    @Singleton
    NetworkUsersListContract.Presenter providesContactsPresenter(PeersAPI api, NetworkUsersPersistance networkUsersPersistance) {
        return new NetworkUsersPresenter(view, api, dataAccount, networkUsersPersistance);
    }
}
