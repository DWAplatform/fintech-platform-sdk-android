package com.fintechplatform.android.enterprise.documents.ui;

import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseDocumentsUIModule {

    private DataAccount configuration;
    private String hostName;

    public EnterpriseDocumentsUIModule(DataAccount configuration, String hostName) {
        this.configuration = configuration;
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    EnterpriseDocumentsUI providesEnterpriseDocumentsUI() {
        return new EnterpriseDocumentsUI(configuration, hostName);
    }
}
