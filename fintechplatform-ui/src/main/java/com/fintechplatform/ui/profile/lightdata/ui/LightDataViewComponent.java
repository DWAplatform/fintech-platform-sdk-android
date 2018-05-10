package com.fintechplatform.ui.profile.lightdata.ui;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.user.api.ProfileAPIModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule;

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
