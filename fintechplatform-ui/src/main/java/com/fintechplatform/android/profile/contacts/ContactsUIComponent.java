package com.fintechplatform.android.profile.contacts;

import com.fintechplatform.android.profile.contacts.ui.ContactsUI;
import com.fintechplatform.android.profile.contacts.ui.ContactsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        ContactsUIModule.class
})
public interface ContactsUIComponent {
    ContactsUI getContactsUI();
}
