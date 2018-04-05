package com.fintechplatform.ui.profile.contacts.ui;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.profile.api.ProfileAPIModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        ContactsPresenterModule.class,
        AlertHelpersModule.class,
        ProfileAPIModule.class,
        NetModule.class,
        UsersPersistanceDBModule.class,
        LogModule.class
})
public interface ContactsViewComponent {
    void inject(ContactsActivity activity);
}
