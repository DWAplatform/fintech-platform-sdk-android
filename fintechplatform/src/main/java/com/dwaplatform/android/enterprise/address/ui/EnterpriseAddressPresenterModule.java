package com.dwaplatform.android.enterprise.address.ui;

import com.dwaplatform.android.enterprise.api.EnterpriseAPI;
import com.dwaplatform.android.enterprise.db.enterprise.EnterprisePersistanceDB;
import com.dwaplatform.android.models.DataAccount;

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
