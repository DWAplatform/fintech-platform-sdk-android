package com.dwaplatform.android.log;

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
