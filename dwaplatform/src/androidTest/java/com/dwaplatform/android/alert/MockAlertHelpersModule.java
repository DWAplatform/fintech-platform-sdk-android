package com.dwaplatform.android.alert;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 12/12/17.
 */
@Module
public class MockAlertHelpersModule {
    @Provides
    @Singleton
    AlertHelpers providesMockAlertHelper(){
        return Mockito.mock(AlertHelpers.class);
    }
}
