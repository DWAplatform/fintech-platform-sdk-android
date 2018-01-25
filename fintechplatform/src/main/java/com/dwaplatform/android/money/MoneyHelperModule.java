package com.dwaplatform.android.money;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ingrid on 08/12/17.
 */

@Module
public class MoneyHelperModule {
    @Provides
    @Singleton
    public MoneyHelper providesMoneyHelper() {
        return new MoneyHelper();
    }
}
