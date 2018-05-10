package com.fintechplatform.ui.profile.jobinfo.ui;

import com.fintechplatform.api.log.LogModule;
import com.fintechplatform.api.net.NetModule;
import com.fintechplatform.api.user.api.ProfileAPIModule;
import com.fintechplatform.ui.alert.AlertHelpersModule;

import com.fintechplatform.ui.profile.db.user.UsersPersistanceDBModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {
        JobInfoPresenterModule.class,
        NetModule.class,
        ProfileAPIModule.class,
        LogModule.class,
        AlertHelpersModule.class,
        UsersPersistanceDBModule.class,

})
public interface JobInfoViewComponent {
    void inject(JobInfoActivity activity);
}
