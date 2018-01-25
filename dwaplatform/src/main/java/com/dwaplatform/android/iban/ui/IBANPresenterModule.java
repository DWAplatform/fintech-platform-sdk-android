package com.dwaplatform.android.iban.ui;


import com.dwaplatform.android.iban.api.IbanAPI;
import com.dwaplatform.android.iban.db.IbanPersistanceDB;
import com.dwaplatform.android.models.DataAccount;
import com.dwaplatform.android.profile.api.ProfileAPI;
import com.dwaplatform.android.profile.db.user.UsersPersistanceDB;

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
                                                 IbanPersistanceDB ibanPersistanceDB,
                                                 UsersPersistanceDB usersPersistanceDB){
        return new IBANPresenter(view, api, profileAPI, configuration, ibanPersistanceDB, usersPersistanceDB);
    }
}
