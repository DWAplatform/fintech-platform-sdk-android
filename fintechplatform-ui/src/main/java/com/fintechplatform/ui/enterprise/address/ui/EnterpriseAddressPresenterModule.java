package com.fintechplatform.ui.enterprise.address.ui;

import com.fintechplatform.api.enterprise.api.EnterpriseAPI;
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB;
import com.fintechplatform.ui.models.DataAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class EnterpriseAddressPresenterModule {

    private EnterpriseAddressContract.View view;
    private DataAccount dataAccount;

    public EnterpriseAddressPresenterModule(EnterpriseAddressContract.View view, DataAccount dataAccount) {
        this.view = view;
        this.dataAccount = dataAccount;
    }

    @Provides
    @Singleton
    EnterpriseAddressContract.Presenter providesAddressPresenter(EnterpriseAPI api, EnterprisePersistanceDB persistanceDB) {
        return new EnterpriseAddressPresenter(view, api, dataAccount, persistanceDB);
    }
}
