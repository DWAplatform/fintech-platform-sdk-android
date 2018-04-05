package com.fintechplatform.android.profile.contacts.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.profile.api.ProfileAPIModule;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDBModule;

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
