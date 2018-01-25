package com.dwaplatform.android.enterprise.info.ui;

import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseInfoUIModule {
    private DataAccount configuration;
    private String hostName;

    public EnterpriseInfoUIModule(String hostName, DataAccount configuration) {
        this.configuration = configuration;
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    EnterpriseInfoUI providesLightDataUI() {
        return new EnterpriseInfoUI(hostName, configuration);
    }
}
