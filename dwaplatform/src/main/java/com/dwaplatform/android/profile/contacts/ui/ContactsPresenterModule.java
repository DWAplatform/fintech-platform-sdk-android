package com.dwaplatform.android.profile.contacts.ui;

import com.dwaplatform.android.auth.keys.KeyChain;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.api.ProfileAPI;
import com.dwaplatform.android.profile.db.UsersPersistanceDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsPresenterModule {
    private ContactsContract.View view;
    private DataAccount configuration;

    public ContactsPresenterModule(ContactsContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    ContactsContract.Presenter providesContactsPresenter(ProfileAPI api, KeyChain keyChain, UsersPersistanceDB usersPersistanceDB) {
        return new ContactsPresenter(view, api, configuration, keyChain, usersPersistanceDB);

    }
}
