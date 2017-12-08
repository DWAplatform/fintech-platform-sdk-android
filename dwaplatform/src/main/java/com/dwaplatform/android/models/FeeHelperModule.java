package com.dwaplatform.android.models;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 08/12/17.
 */

@Module
public class FeeHelperModule {
    @Provides
    @Singleton
    FeeHelper providesFeeHelper() {
        return new FeeHelper();
    }
}
