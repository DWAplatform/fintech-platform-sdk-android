package com.fintechplatform.android.profile.address.ui;

import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AddressUIModule {

    private String hostName;
    private DataAccount dataAccount;

    public AddressUIModule(String hostName, DataAccount dataAccount) {
        this.hostName = hostName;
        this.dataAccount = dataAccount;
    }

    @Provides
    @Singleton
    AddressUI providesAddressUI() {
        return new AddressUI(hostName, dataAccount);
    }
}
