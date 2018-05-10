package com.fintechplatform.ui.profile.lightdata.ui;

import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.api.user.api.ProfileAPI;
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB;

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
    LightDataContract.Presenter providesLightDataPresenter(ProfileAPI api, UsersPersistanceDB usersPersistanceDB) {
        return new LightDataPresenter(view, api, configuration, usersPersistanceDB);
    }
}
