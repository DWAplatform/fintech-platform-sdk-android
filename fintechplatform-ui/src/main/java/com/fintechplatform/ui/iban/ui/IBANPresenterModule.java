package com.fintechplatform.ui.iban.ui;


import com.fintechplatform.api.enterprise.api.EnterpriseAPI;
import com.fintechplatform.ui.enterprise.db.enterprise.EnterprisePersistanceDB;
import com.fintechplatform.api.iban.api.IbanAPI;
import com.fintechplatform.ui.iban.db.IbanPersistanceDB;
import com.fintechplatform.ui.models.DataAccount;
import com.fintechplatform.api.profile.api.ProfileAPI;
import com.fintechplatform.ui.profile.db.user.UsersPersistanceDB;

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
