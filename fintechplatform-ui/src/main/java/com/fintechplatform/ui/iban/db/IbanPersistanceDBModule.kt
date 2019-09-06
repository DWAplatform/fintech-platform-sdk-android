package com.fintechplatform.ui.iban.db

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class IbanPersistanceDBModule {

    @Provides
    @Singleton
    internal fun providesIbanPersistance(ibanDB: IbanDB): IbanPersistanceDB {
        return IbanPersistanceDB(ibanDB)
    }

    @Provides
    @Singleton
    internal fun providesIbanDB(): IbanDB {
        return IbanDB()
    }
}