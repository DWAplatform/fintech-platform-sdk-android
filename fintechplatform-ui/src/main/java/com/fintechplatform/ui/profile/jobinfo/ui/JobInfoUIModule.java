package com.fintechplatform.ui.profile.jobinfo.ui;

import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class JobInfoUIModule {

    private String hostName;
    private DataAccount configuration;

    public JobInfoUIModule(String hostName, DataAccount configuration) {
        this.hostName = hostName;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    JobInfoUI providesJobInfoUI(){
        return new JobInfoUI(hostName, configuration);
    }
}
