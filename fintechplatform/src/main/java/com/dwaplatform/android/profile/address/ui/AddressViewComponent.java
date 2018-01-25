package com.dwaplatform.android.profile.address.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.profile.api.ProfileAPIModule;
import com.dwaplatform.android.profile.db.user.UsersPersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        AddressPresenterModule.class,
        ProfileAPIModule.class,
        NetModule.class,
        LogModule.class,
        AlertHelpersModule.class,
        UsersPersistanceDBModule.class
})
public interface AddressViewComponent {
    void inject(AddressActivity activity);
}
