package com.fintechplatform.android.money;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FeeHelperModule {
    @Provides
    @Singleton
    FeeHelper providesFeeHelper() {
        return new FeeHelper();
    }
}
