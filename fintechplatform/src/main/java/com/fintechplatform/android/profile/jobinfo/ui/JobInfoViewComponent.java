package com.fintechplatform.android.profile.jobinfo.ui;

import com.fintechplatform.android.alert.AlertHelpersModule;
import com.fintechplatform.android.api.NetModule;
import com.fintechplatform.android.log.LogModule;
import com.fintechplatform.android.profile.api.ProfileAPIModule;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDBModule;

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
