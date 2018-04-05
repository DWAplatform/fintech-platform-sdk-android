package com.fintechplatform.api.sample.contactslist.ui;

import com.fintechplatform.api.alert.AlertHelpersModule;
import com.fintechplatform.api.images.ImageHelperModule;
import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.net.NetModule;
import com.fintechplatform.api.sample.contactslist.api.PeersApiModule;
import com.fintechplatform.api.sample.contactslist.models.NetworkUsersPersistanceModule;

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
