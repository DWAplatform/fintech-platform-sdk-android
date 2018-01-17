package com.dwaplatform.android.profile.jobinfo.ui;

import com.dwaplatform.android.alert.AlertHelpersModule;
import com.dwaplatform.android.api.NetModule;
import com.dwaplatform.android.log.LogModule;
import com.dwaplatform.android.profile.api.ProfileAPIModule;
import com.dwaplatform.android.profile.db.user.UsersPersistanceDBModule;

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
