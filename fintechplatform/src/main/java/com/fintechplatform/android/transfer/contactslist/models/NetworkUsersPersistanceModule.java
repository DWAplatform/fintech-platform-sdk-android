package com.fintechplatform.android.transfer.contactslist.models;


import com.fintechplatform.android.transfer.contactslist.db.NetworkUsersDB;
import com.fintechplatform.android.transfer.contactslist.db.NetworkUsersPersistance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkUsersPersistanceModule {
    @Provides
    @Singleton
    NetworkUsersPersistance providesP2PPersistance(NetworkUsersDB p2PDB) {
        return new NetworkUsersPersistance(p2PDB);
    }

    @Provides
    @Singleton
    NetworkUsersDB providesP2Pdb() {
        return new NetworkUsersDB();
    }

    @Singleton
    @Provides
    NetworkUsersManager providesP2PManager() {
        return new NetworkUsersManager();
    }
}
