package com.dwaplatform.android.enterprise.info.ui;

import com.dwaplatform.android.enterprise.api.EnterpriseProfileAPI;
import com.dwaplatform.android.enterprise.db.enterprise.EnterprisePersistanceDB;
import com.dwaplatform.android.models.DataAccount;

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
    EnterpriseInfoContract.Presenter providesLightDataPresenter(EnterpriseProfileAPI api, EnterprisePersistanceDB persistanceDB) {
        return new EnterpriseInfoPresenter(view, api, configuration, persistanceDB);
    }
}
