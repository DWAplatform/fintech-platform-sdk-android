package com.fintechplatform.android.sample.contactslist;

import com.fintechplatform.android.sample.contactslist.ui.NetworkUsersListUI;
import com.fintechplatform.android.sample.contactslist.ui.NetworkUsersUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {NetworkUsersUIModule.class})
public interface NetworkListUIComponent {
    NetworkUsersListUI getNetworkListUI();
}
