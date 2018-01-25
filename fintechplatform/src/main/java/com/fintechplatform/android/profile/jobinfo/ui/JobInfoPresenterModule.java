package com.fintechplatform.android.profile.jobinfo.ui;

import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.profile.api.ProfileAPI;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class JobInfoPresenterModule {

    private JobInfoContract.View view;
    private DataAccount configuration;

    public JobInfoPresenterModule(JobInfoContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    JobInfoContract.Presenter providesJobInfoPresenter(ProfileAPI api, UsersPersistanceDB usersPersistanceDB) {
        return new JobInfoPresenter(view, api, configuration, usersPersistanceDB);
    }
}
