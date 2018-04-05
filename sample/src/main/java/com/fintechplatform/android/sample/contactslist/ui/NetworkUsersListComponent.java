package com.fintechplatform.android.sample.contactslist.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.images.ImageHelperModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.net.NetModule;
import com.fintechplatform.android.sample.contactslist.api.PeersApiModule;
import com.fintechplatform.android.sample.contactslist.models.NetworkUsersPersistanceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        NetworkUsersListPresenterModule.class,
        NetModule.class,
        LogModule.class,
        PeersApiModule.class,
        ImageHelperModule.class,
        NetworkUsersPersistanceModule.class,
        AlertHelpersModule.class
})
public interface NetworkUsersListComponent {
    void inject(NetworkUsersListActivity activity);
}
