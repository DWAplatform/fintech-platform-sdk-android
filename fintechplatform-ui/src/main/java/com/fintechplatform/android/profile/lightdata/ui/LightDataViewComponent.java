package com.fintechplatform.android.profile.lightdata.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.net.NetModule;
import com.fintechplatform.android.profile.api.ProfileAPIModule;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        LightDataPresenterModule.class,
        ProfileAPIModule.class,
        NetModule.class,
        LogModule.class,
        AlertHelpersModule.class,
        UsersPersistanceDBModule.class
})
public interface LightDataViewComponent {
    void inject(LightDataActivity activity);
}
