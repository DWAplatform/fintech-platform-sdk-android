package com.fintechplatform.ui.profile.db.user;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UsersPersistanceDBModule {

    @Provides
    @Singleton
    UsersPersistanceDB providesUsersPersistanceDB(UsersDB usersDB) {
        return new UsersPersistanceDB(usersDB);
    }

    @Provides
    @Singleton
    UsersDB providesUserDB() {
        return new UsersDB();
    }
}
