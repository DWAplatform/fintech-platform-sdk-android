package com.fintechplatform.android.sample.contactslist.ui;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.sample.contactslist.db.NetworkUsersPersistance;

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
    NetworkUsersListContract.Presenter providesContactsPresenter(NetworkUsersPersistance networkUsersPersistance) {
        return new NetworkUsersPresenter(view, dataAccount, networkUsersPersistance);
    }
}
