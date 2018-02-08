package com.dwafintech.dwapay.main.networklist;

import com.fintechplatform.android.transfer.ui.TransferUIModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        NetworkUsersUIModule.class,
        TransferUIModule.class})
public interface NetworkListUIComponent {
    NetworkUsersListUI getNetworkListUI();
}
