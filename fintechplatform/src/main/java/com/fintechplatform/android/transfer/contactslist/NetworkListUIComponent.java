package com.fintechplatform.android.transfer.contactslist;

import com.fintechplatform.android.transfer.contactslist.ui.NetworkUsersListUI;
import com.fintechplatform.android.transfer.contactslist.ui.NetworkUsersUIModuleOLD;
import com.fintechplatform.android.transfer.ui.TransferUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {NetworkUsersUIModuleOLD.class,
        TransferUIModule.class})
public interface NetworkListUIComponent {
    NetworkUsersListUI getNetworkListUI();
}
