package com.fintechplatform.ui.enterprise.address.ui;

import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseAddressUIModule {

    private String hostName;
    private DataAccount dataAccount;

    public EnterpriseAddressUIModule(String hostName, DataAccount dataAccount) {
        this.hostName = hostName;
        this.dataAccount = dataAccount;
    }

    @Provides
    @Singleton
    EnterpriseAddressUI providesAddressUI() {
        return new EnterpriseAddressUI(hostName, dataAccount);
    }
}
