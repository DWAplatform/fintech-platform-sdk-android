package com.dwaplatform.android.profile.contacts;

import com.dwaplatform.android.profile.contacts.ui.ContactsUI;
import com.dwaplatform.android.profile.contacts.ui.ContactsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        ContactsUIModule.class
})
public interface ContactsUIComponent {
    ContactsUI getContactsUI();
}
