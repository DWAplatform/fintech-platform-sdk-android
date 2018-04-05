package com.fintechplatform.ui.money;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoneyHelperModule {
    @Provides
    @Singleton
    public MoneyHelper providesMoneyHelper() {
        return new MoneyHelper();
    }
}
