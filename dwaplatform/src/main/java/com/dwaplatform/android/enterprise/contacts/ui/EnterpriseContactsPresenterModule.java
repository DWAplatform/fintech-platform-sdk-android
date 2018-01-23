package com.dwaplatform.android.enterprise.contacts.ui;

import com.dwaplatform.android.enterprise.api.EnterpriseAPI;
import com.dwaplatform.android.enterprise.db.enterprise.EnterprisePersistanceDB;
import com.dwaplatform.android.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseContactsPresenterModule {
    private EnterpriseContactsContract.View view;
    private DataAccount configuration;

    public EnterpriseContactsPresenterModule(EnterpriseContactsContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    EnterpriseContactsContract.Presenter providesContactsPresenter(EnterpriseAPI api, EnterprisePersistanceDB persistanceDB) {
        return new EnterpriseContactsPresenter(view, api, configuration, persistanceDB);

    }
}
