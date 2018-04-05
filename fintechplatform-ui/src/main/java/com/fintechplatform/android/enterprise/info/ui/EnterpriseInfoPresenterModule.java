package com.fintechplatform.android.enterprise.info.ui;

import com.fintechplatform.android.enterprise.api.EnterpriseAPI;
import com.fintechplatform.android.enterprise.db.enterprise.EnterprisePersistanceDB;
import com.fintechplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseInfoPresenterModule {

    private EnterpriseInfoContract.View view;
    private DataAccount configuration;

    public EnterpriseInfoPresenterModule(EnterpriseInfoContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    EnterpriseInfoContract.Presenter providesLightDataPresenter(EnterpriseAPI api, EnterprisePersistanceDB persistanceDB) {
        return new EnterpriseInfoPresenter(view, api, configuration, persistanceDB);
    }
}
