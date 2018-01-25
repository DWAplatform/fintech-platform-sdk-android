package com.fintechplatform.android.log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tcappellari on 08/12/2017.
 */

@Module
public class LogModule {

    @Provides
    @Singleton
    Log provideLog() {
        return new Log();
    }

}
