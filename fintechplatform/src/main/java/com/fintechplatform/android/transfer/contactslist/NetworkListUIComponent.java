package com.fintechplatform.android.transfer.contactslist;

import com.fintechplatform.android.transfer.contactslist.ui.NetworkUsersListUI;
import com.fintechplatform.android.transfer.contactslist.ui.NetworkUsersUIModule;
import com.fintechplatform.android.transfer.ui.TransferUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {NetworkUsersUIModule.class,
        TransferUIModule.class})
public interface NetworkListUIComponent {
    NetworkUsersListUI getNetworkListUI();
}
