package com.fintechplatform.android.profile.contacts.ui;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.profile.api.ProfileAPI;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB;

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
    ContactsContract.Presenter providesContactsPresenter(ProfileAPI api, UsersPersistanceDB usersPersistanceDB) {
        return new ContactsPresenter(view, api, configuration, usersPersistanceDB);

    }
}
