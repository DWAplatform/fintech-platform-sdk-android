package com.fintechplatform.ui.profile.contacts;

import com.fintechplatform.ui.profile.contacts.ui.ContactsUI;
import com.fintechplatform.ui.profile.contacts.ui.ContactsUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        ContactsUIModule.class
})
public interface ContactsUIComponent {
    ContactsUI getContactsUI();
}
