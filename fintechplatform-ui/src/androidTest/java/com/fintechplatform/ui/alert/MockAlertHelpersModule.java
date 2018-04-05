package com.fintechplatform.ui.alert;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockAlertHelpersModule {
    @Provides
    @Singleton
    AlertHelpers providesMockAlertHelper(){
        return Mockito.mock(AlertHelpers.class);
    }
}
