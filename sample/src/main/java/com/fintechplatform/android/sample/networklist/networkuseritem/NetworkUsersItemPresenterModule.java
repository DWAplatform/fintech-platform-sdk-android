package com.dwafintech.dwapay.main.networklist.networkuseritem;

import com.dwafintech.dwapay.main.networklist.db.NetworkUsersPersistance;

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
