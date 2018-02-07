package com.fintechplatform.android.transfer.contactslist.ui.networkuseritem;

import com.fintechplatform.android.transfer.contactslist.db.NetworkUsersPersistance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkUsersItemPresenterModule {

    private NetworkUserItemContract.View view;

    public NetworkUsersItemPresenterModule(NetworkUserItemContract.View view) {
        this.view = view;
    }

    @Provides
    @Singleton
    NetworkUserItemContract.Presenter providesNetworkItemPresenter(NetworkUsersPersistance networkUserspersistance) {
        return new NetworkUserItemPresenter(view, networkUserspersistance);
    }
}
