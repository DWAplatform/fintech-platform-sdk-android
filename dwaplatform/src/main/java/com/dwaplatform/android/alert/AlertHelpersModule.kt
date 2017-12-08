package com.dwaplatform.android.alert

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by tcappellari on 08/12/2017.
 */

/**
 * Created by ingrid on 07/09/17.
 */

@Module
class AlertHelpersModule {

    @Provides
    @Singleton
    internal fun provideAlertHelpers(): AlertHelpers {
        return AlertHelpers()
    }
}

