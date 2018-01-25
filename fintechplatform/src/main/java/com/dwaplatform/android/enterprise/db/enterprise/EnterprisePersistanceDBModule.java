package com.dwaplatform.android.enterprise.db.enterprise;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterprisePersistanceDBModule {
    @Provides
    @Singleton
    EnterprisePersistanceDB providesPersistanceDB(EnterpriseDB enterpriseDB) {
        return new EnterprisePersistanceDB(enterpriseDB);
    }

    @Provides
    @Singleton
    EnterpriseDB providesDB() {
        return new EnterpriseDB();
    }
}
