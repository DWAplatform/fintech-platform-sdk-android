package com.fintechplatform.android.transfer.contactslist.ui;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.transfer.ui.TransferUI;

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
    NetworkUsersListUI providesNetworkList(TransferUI transferUI) {
        return new NetworkUsersListUI(hostName, dataAccount, transferUI);
    }
}
