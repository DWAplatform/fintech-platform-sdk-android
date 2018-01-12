package com.dwaplatform.android.profile.jobinfo.ui;

import com.dwaplatform.android.auth.keys.KeyChain;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.api.ProfileAPI;
import com.dwaplatform.android.profile.db.user.UsersPersistanceDB;

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
    JobInfoContract.Presenter providesJobInfoPresenter(ProfileAPI api, KeyChain keyChain, UsersPersistanceDB usersPersistanceDB) {
        return new JobInfoPresenter(view, api, configuration, keyChain, usersPersistanceDB);
    }
}
