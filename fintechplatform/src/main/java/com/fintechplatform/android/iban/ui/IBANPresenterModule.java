package com.fintechplatform.android.iban.ui;


import com.fintechplatform.android.enterprise.api.EnterpriseAPI;
import com.fintechplatform.android.enterprise.db.enterprise.EnterprisePersistanceDB;
import com.fintechplatform.android.iban.api.IbanAPI;
import com.fintechplatform.android.iban.db.IbanPersistanceDB;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.profile.api.ProfileAPI;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IBANPresenterModule {

    private IBANContract.View view;
    private DataAccount configuration;

    public IBANPresenterModule(IBANContract.View view, DataAccount configuration){
        this.view = view;
        this.configuration = configuration;
    }

    @Singleton
    @Provides
    IBANContract.Presenter providesIBanPresenter(IbanAPI api,
                                                 ProfileAPI profileAPI,
                                                 EnterpriseAPI enterpriseAPI,
                                                 IbanPersistanceDB ibanPersistanceDB,
                                                 UsersPersistanceDB usersPersistanceDB,
                                                 EnterprisePersistanceDB enterprisePersistanceDB){
        return new IBANPresenter(view, api, profileAPI, enterpriseAPI, configuration, ibanPersistanceDB, usersPersistanceDB, enterprisePersistanceDB);
    }
}
