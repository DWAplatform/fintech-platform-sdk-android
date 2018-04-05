package com.fintechplatform.android.alert;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AlertHelpersModule {

    @Provides
    @Singleton
    public AlertHelpers provideAlertHelpers() {
        return new AlertHelpers();
    }
}
