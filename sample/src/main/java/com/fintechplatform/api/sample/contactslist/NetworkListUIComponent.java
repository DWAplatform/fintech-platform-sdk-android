package com.fintechplatform.api.sample.contactslist;

import com.fintechplatform.api.sample.contactslist.ui.NetworkUsersListUI;
import com.fintechplatform.api.sample.contactslist.ui.NetworkUsersUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {NetworkUsersUIModule.class})
public interface NetworkListUIComponent {
    NetworkUsersListUI getNetworkListUI();
}
