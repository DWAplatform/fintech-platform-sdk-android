package com.dwaplatform.android.profile.lightdata.ui;

import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LightDataUIModule {
    private DataAccount configuration;
    private String hostName;

    public LightDataUIModule(String hostName, DataAccount configuration) {
        this.configuration = configuration;
        this.hostName = hostName;
    }

    @Provides
    @Singleton
    LightDataUI providesLightDataUI() {
        return new LightDataUI(hostName, configuration);
    }
}
