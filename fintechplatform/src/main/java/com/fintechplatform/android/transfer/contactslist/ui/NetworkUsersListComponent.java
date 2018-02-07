package com.fintechplatform.android.transfer.contactslist.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.images.ImageHelperModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.transfer.api.TransferAPIModule;
import com.fintechplatform.android.transfer.contactslist.models.NetworkUsersPersistanceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        NetworkUsersListPresenterModule.class,
        TransferAPIModule.class,
        NetModule.class,
        LogModule.class,
        ImageHelperModule.class,
        NetworkUsersPersistanceModule.class,
        AlertHelpersModule.class,
        TransfersUIHelperModule.class
})
public interface NetworkUsersListComponent {
    void inject(NetworkUsersListActivity activity);
}
