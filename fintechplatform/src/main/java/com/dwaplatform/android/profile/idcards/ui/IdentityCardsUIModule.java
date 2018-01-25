package com.dwaplatform.android.profile.idcards.ui;

import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IdentityCardsUIModule {

    private String hostName;
    private DataAccount configuration;

    public IdentityCardsUIModule(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    IdentityCardsUI providesIdCardsUI(){
        return new IdentityCardsUI(hostName, configuration);
    }
}
