package com.fintechplatform.android.sample.contactslist.ui;

import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkUsersUIModule {

    private DataAccount dataAccount;
    private String hostName;

    public NetworkUsersUIModule(DataAccount dataAccount, String hostName) {
        this.dataAccount = dataAccount;
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    NetworkUsersListUI providesNetworkList() {
        return new NetworkUsersListUI(hostName, dataAccount);
    }
}
