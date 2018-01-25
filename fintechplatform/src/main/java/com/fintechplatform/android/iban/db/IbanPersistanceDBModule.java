package com.fintechplatform.android.iban.db;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IbanPersistanceDBModule {

    @Provides
    @Singleton
    IbanPersistanceDB providesIbanPersistance(IbanDB ibanDB) {
        return new IbanPersistanceDB(ibanDB);
    }

    @Provides
    @Singleton
    IbanDB providesIbanDB() {
        return new IbanDB();
    }
}
