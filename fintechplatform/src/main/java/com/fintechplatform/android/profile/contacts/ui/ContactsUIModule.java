package com.fintechplatform.android.profile.contacts.ui;

import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsUIModule {

    private String hostname;
    private DataAccount configuration;

    public ContactsUIModule(String hostname, DataAccount configuration) {
        this.hostname = hostname;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    ContactsUI providesContactsUI() {
        return new ContactsUI(hostname, configuration);
    }
}
