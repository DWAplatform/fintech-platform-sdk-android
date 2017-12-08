package com.dwaplatform.android.alert;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 08/12/17.
 */

@Module
public class AlertHelpersModule {

    @Provides
    @Singleton
    public AlertHelpers provideAlertHelpers() {
        return new AlertHelpers();
    }
}
