package com.dwaplatform.android.profile.lightdata.ui;

import com.dwaplatform.android.auth.keys.KeyChain;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.api.ProfileAPI;
import com.dwaplatform.android.profile.db.user.UsersPersistanceDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LightDataPresenterModule {

    private LightDataContract.View view;
    private DataAccount configuration;

    public LightDataPresenterModule(LightDataContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    LightDataContract.Presenter providesLightDataPresenter(ProfileAPI api, KeyChain key, UsersPersistanceDB usersPersistanceDB) {
        return new LightDataPresenter(view, api, configuration, key, usersPersistanceDB);
    }
}
