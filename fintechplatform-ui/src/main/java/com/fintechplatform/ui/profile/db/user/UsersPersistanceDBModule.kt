package com.fintechplatform.ui.profile.db.user

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UsersPersistanceDBModule {

    @Provides
    @Singleton
    internal fun providesUsersPersistanceDB(usersDB: UsersDB): UsersPersistanceDB {
        return UsersPersistanceDB(usersDB)
    }

    @Provides
    @Singleton
    internal fun providesUserDB(): UsersDB {
        return UsersDB()
    }
}
