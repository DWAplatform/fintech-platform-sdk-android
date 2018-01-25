package com.dwaplatform.android.enterprise.contacts.ui;

import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseContactsUIModule {

    private String hostname;
    private DataAccount configuration;

    public EnterpriseContactsUIModule(String hostname, DataAccount configuration) {
        this.hostname = hostname;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    EnterpriseContactsUI providesContactsUI() {
        return new EnterpriseContactsUI(hostname, configuration);
    }
}
