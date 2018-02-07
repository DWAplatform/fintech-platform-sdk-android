package com.fintechplatform.android.transfer.contactslist.ui;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transfer.api.TransferAPI;
import com.fintechplatform.android.transfer.contactslist.db.NetworkUsersPersistance;

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
    NetworkUsersListContract.Presenter providesContactsPresenter(TransferAPI api, NetworkUsersPersistance networkUsersPersistance) {
        return new NetworkUsersPresenter(view, api, dataAccount, networkUsersPersistance);
    }
}
