package com.fintechplatform.android.profile.idcards.ui;

import com.fintechplatform.android.images.ImageHelper;
import com.fintechplatform.android.models.DataAccount;
import com.fintechplatform.android.profile.api.ProfileAPI;
import com.fintechplatform.android.profile.db.documents.DocumentsPersistanceDB;
import com.fintechplatform.android.profile.db.user.UsersPersistanceDB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IdentityCardsPresenterModule {

    private IdentityCardsContract.View view;
    private DataAccount configuration;

    public IdentityCardsPresenterModule(IdentityCardsContract.View view, DataAccount configuration) {
        this.view = view;
        this.configuration = configuration;
    }

    @Provides
    @Singleton
    IdentityCardsContract.Presenter providesIdentityCardsPresenter(ProfileAPI api,
                                                                   DocumentsPersistanceDB documents,
                                                                   UsersPersistanceDB users,
                                                                   ImageHelper imageHelper) {
        return new IdentityCardsPresenter(view, api, configuration, documents, users, imageHelper);
    }
}
