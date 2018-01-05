package com.dwaplatform.android.iban.db;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IbanPersistanceDBModule {

    @Provides
    @Singleton
    IbanPersistanceDB providesIbanPersistance() {
        return new IbanPersistanceDB(new IbanDB());
    }
}
