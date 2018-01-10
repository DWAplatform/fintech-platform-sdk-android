package com.dwaplatform.android.profile.contacts.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.auth.keys.KeyChainModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.profile.api.ProfileAPIModule;
import com.dwaplatform.android.profile.db.user.UsersPersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        ContactsPresenterModule.class,
        AlertHelpersModule.class,
        ProfileAPIModule.class,
        NetModule.class,
        KeyChainModule.class,
        UsersPersistanceDBModule.class,
        LogModule.class
})
public interface ContactsViewComponent {
    void inject(ContactsActivity activity);
}
